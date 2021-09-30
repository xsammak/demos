package org.xsammak.demos.scaleapi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Stored weighing record. Uses kg as weigth unit.
 *
 * @author xsammak
 */
@Data
@EqualsAndHashCode
@ToString
public class WeighingRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private String scaleId;

    @NotNull
    private String truckId;

    @NotNull
    private BigDecimal weightKg;

    @NotNull
    private Date time;
}