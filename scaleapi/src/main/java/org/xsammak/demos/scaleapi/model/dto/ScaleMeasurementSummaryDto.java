package org.xsammak.demos.scaleapi.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Response DTO for filtered scale measurement summary.
 *
 * @author xsammak
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(callSuper = true)
@ApiModel(description = "Filtered scale measurement summary")
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"scale_id", "truck_id", "month", "weight", "unit"})
public class ScaleMeasurementSummaryDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("scale_id")
    @ApiModelProperty(value = "ID of the weighing scale", position = 1, example = "bsg_768")
    private String scaleId;

    @JsonProperty("truck_id")
    @ApiModelProperty(value = "ID of the truck carrying the load", position = 2, example = "trckOTK838")
    private String truckId;

    @ApiModelProperty(value = "Month of the filtered loads", position = 3, example = "June")
    private String month;

    @ApiModelProperty(value = "Weight of the filtered loads", position = 4, example = "1120")
    private BigDecimal weight;

    @ApiModelProperty(value = "Units of the load", position = 5, example = "kg")
    private String unit;
}
