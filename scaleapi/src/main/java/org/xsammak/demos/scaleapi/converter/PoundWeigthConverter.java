package org.xsammak.demos.scaleapi.converter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Converts pounds to/from Kg.
 */
public class PoundWeigthConverter implements WeigthConverter {

    private static final BigDecimal FACTOR = BigDecimal.valueOf(2.2046);

    @Override
    public String getUnit() {
        return "pound";
    }

    @Override
    public BigDecimal fromKg(BigDecimal weigthKg) {
        return weigthKg.multiply(FACTOR).setScale(0, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal toKg(BigDecimal weigth) {
        return weigth.divide(FACTOR, 0, RoundingMode.HALF_UP);
    }
}
