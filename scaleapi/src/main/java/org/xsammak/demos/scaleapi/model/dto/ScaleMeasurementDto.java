package org.xsammak.demos.scaleapi.model.dto;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * DTO for scale measurement used in JSON file.
 *
 * @author xsammak
 */
@Data
@EqualsAndHashCode
@ToString
@ApiModel(description = "Scale measurement record")
@JsonPropertyOrder({"scale_id", "truck_id", "weigth", "unit", "time"})
public class ScaleMeasurementDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @JsonProperty("scale_id")
    @ApiModelProperty(value = "ID of the weighing scale", position = 1, example = "bsg_768")
    private String scaleId;

    @NotNull
    @JsonProperty("truck_id")
    @ApiModelProperty(value = "ID of the truck carrying the load", position = 2, example = "trckOTK838")
    private String truckId;

    @NotNull
    @ApiModelProperty(value = "Weight of the load carried in the truck", position = 3, example = "1120")
    private BigDecimal weight;

    @NotNull
    @ApiModelProperty(value = "Units of the load", position = 4, example = "kg")
    private String unit;

    @NotNull
    @ApiModelProperty(value = "Time when the weighing is performed", position = 5, example = "2021-06-18T10:12:45")
    private Date time;
}
