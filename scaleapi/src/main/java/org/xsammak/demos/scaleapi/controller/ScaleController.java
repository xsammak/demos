package org.xsammak.demos.scaleapi.controller;

import java.util.List;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

import org.xsammak.demos.scaleapi.model.dto.ScaleMeasurementSummaryDto;
import org.xsammak.demos.scaleapi.model.dto.StatusMessageDto;
import org.xsammak.demos.scaleapi.model.dto.StatusMessageType;
import org.xsammak.demos.scaleapi.model.dto.ScaleMeasurementDto;
import org.xsammak.demos.scaleapi.service.WeighingService;

/**
 * REST API controller for scale demo.
 *
 * @author xsammak
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/scales")
@CrossOrigin(origins = "*")
@Api(value = "scale-demo")
public class ScaleController {

    private final Jackson2ObjectMapperBuilder mapperBuilder;
    
    private final WeighingService weighingService;

    @ApiOperation(value = "Import scale measurement JSON file")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "File imported successfully", response = StatusMessageDto.class) })
    @PostMapping(value = "/import", produces = "application/json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public StatusMessageDto importScaleMeasurementFile(@ApiParam(value = "Scale measurement JSON file", required = true) @RequestParam("file") MultipartFile file) throws IOException {
        weighingService.saveWeighingData(convertToDto(file.getBytes()));
        return new StatusMessageDto(StatusMessageType.INFO, "File imported successfully, name: " + file.getOriginalFilename());
    }

    @ApiOperation(value = "Get filtered scale measurement summary")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Filtered scale measurement summary", response = ScaleMeasurementSummaryDto.class) })
    @GetMapping(value = "/summary/filter", produces = "application/json")
    @ResponseBody
    public ScaleMeasurementSummaryDto filterScaleMeasurements(
            @ApiParam(value = "ID of the weighing scale", required = false) @RequestParam(required = false) String scale_id,
            @ApiParam(value = "ID of the truck carrying the load", required = false) @RequestParam(required = false) String truck_id,
            @ApiParam(value = "Unit of the returned weight", required = false) @RequestParam(required = false) String unit,
            @ApiParam(value = "Month when the weighing was performed", required = false) @RequestParam(required = false) String month) throws IOException {
        return weighingService.filterWeighingDataSummary(truck_id, scale_id, unit, month);
    }

    private List<ScaleMeasurementDto> convertToDto(byte[] bytes) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = mapperBuilder.build();
        return mapper.readValue(new String(bytes, StandardCharsets.UTF_8), new TypeReference<List<ScaleMeasurementDto>>() {});
    }
}
