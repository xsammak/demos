package org.xsammak.demos.scaleapi.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.xsammak.demos.scaleapi.converter.WeigthConverterFactory;
import org.xsammak.demos.scaleapi.mapper.DtoModelMapper;
import org.xsammak.demos.scaleapi.model.WeighingRecord;
import org.xsammak.demos.scaleapi.model.dto.ScaleMeasurementDto;
import org.xsammak.demos.scaleapi.model.dto.ScaleMeasurementSummaryDto;

import lombok.RequiredArgsConstructor;

/**
 * Service for accessing stored weighing data.
 *
 * @author xsammak
 */
@Service
@RequiredArgsConstructor
@Validated
public class WeighingService {

    private final WeighingStorageService weighingStorageService;

    public void saveWeighingData(List<@Valid ScaleMeasurementDto> data) throws IOException {
        weighingStorageService
                .storeWeighingData(data.stream().map(DtoModelMapper::convertToModel).collect(Collectors.toList()));
    }

    public ScaleMeasurementSummaryDto filterWeighingDataSummary(String truckId, String scaleId, String unit,
            String month) throws IOException {
        validateMonth(month);
        List<WeighingRecord> weighingData = weighingStorageService.getWeighingData();
        BigDecimal weigthSum = weighingData.stream()
                .filter(e -> StringUtils.isEmpty(truckId) || Objects.equals(truckId, e.getTruckId()))
                .filter(e -> StringUtils.isEmpty(scaleId) || Objects.equals(scaleId, e.getScaleId()))
                .filter(e -> StringUtils.isEmpty(month) || Objects.equals(month, getMonth(e.getTime())))
                .map(WeighingRecord::getWeightKg).reduce(BigDecimal.ZERO, BigDecimal::add);

        String weightUnit = StringUtils.hasText(unit) ? unit : "kg";
        return new ScaleMeasurementSummaryDto(truckId, scaleId, month,
                WeigthConverterFactory.getWeigthConverter(weightUnit).fromKg(weigthSum), weightUnit);
    }

    private String getMonth(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonth().getDisplayName(TextStyle.FULL,
                Locale.ENGLISH);
    }

    private void validateMonth(String givenMonth) {
        if (StringUtils.hasText(givenMonth)) {
            Arrays.asList(new DateFormatSymbols().getMonths()).stream()
                    .filter(month -> Objects.equals(month, givenMonth)).findAny()
                    .orElseThrow(() -> new InvalidMonthException("Month is invalid '" + givenMonth + "'"));
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public class InvalidMonthException extends RuntimeException {
        public InvalidMonthException(String message) {
            super(message);
        }
    }
}
