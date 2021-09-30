package org.xsammak.demos.scaleapi.converter;

import java.math.BigDecimal;

/**
 * Converts Kg to/from Kg, so acts as dummy converter.
 */
public class KgWeigthConverter implements WeigthConverter {

    @Override
    public String getUnit() {
        return "kg";
    }

    @Override
    public BigDecimal fromKg(BigDecimal weigthKg) {
        return weigthKg;
    }

    @Override
    public BigDecimal toKg(BigDecimal weigth) {
        return weigth;
    }
}
