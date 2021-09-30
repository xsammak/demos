package org.xsammak.demos.scaleapi.converter;

import java.math.BigDecimal;

/**
 * Interface to convert weigths from/to Kg.
 */
public interface WeigthConverter {
    String getUnit();
    BigDecimal fromKg(BigDecimal weigthKg);
    BigDecimal toKg(BigDecimal weigth);
}
