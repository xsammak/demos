package org.xsammak.demos.scaleapi.converter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Converts metric tons to/from Kg.
 */
public class MetricTonWeigthConverter implements WeigthConverter {

    private static final BigDecimal FACTOR = BigDecimal.valueOf(1000);

    @Override
    public String getUnit() {
        return "ton";
    }

    @Override
    public BigDecimal fromKg(BigDecimal weigthKg) {
        return weigthKg.divide(FACTOR, 0, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal toKg(BigDecimal weigth) {
        return weigth.multiply(FACTOR);
    }
}
