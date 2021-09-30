package org.xsammak.demos.scaleapi.mapper;

import org.xsammak.demos.scaleapi.model.WeighingRecord;
import org.xsammak.demos.scaleapi.model.dto.ScaleMeasurementDto;
import org.xsammak.demos.scaleapi.converter.WeigthConverterFactory;

import lombok.experimental.UtilityClass;

/**
 * Maps DTO to model object. Converts also weigth to Kg.
 */
@UtilityClass
public class DtoModelMapper {

    public static WeighingRecord convertToModel(ScaleMeasurementDto dto) {
        WeighingRecord model = new WeighingRecord();
        model.setScaleId(dto.getScaleId());
        model.setTruckId(dto.getTruckId());
        model.setTime(dto.getTime());
        model.setWeightKg(WeigthConverterFactory.getWeigthConverter(dto.getUnit()).toKg(dto.getWeight()));
        return model;
    }
}
