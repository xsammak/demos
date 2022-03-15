package org.xsammak.demos.scaleapi.converter;

import java.util.Objects;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.experimental.UtilityClass;

/**
 * Factory class to get weigth converters based on unit. New units can be supported without changing existing code.
 */
@UtilityClass
public class WeigthConverterFactory {

    private static final ServiceLoader<WeigthConverter> LOADER = ServiceLoader.load(WeigthConverter.class);

    public static synchronized WeigthConverter getWeigthConverter(String unit) {
        return StreamSupport.stream(LOADER.spliterator(), false)
                .filter(provider -> Objects.equals(unit, provider.getUnit())).findFirst()
                .orElseThrow(() -> new ConverterNotFoundException("No converter found for the weigth unit '" + unit + "'"));
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public static class ConverterNotFoundException extends RuntimeException {
        public ConverterNotFoundException(String message) {
            super(message);
        }
    }
}
