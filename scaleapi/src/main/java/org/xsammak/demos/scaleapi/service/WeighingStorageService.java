package org.xsammak.demos.scaleapi.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Service;
import org.xsammak.demos.scaleapi.model.WeighingRecord;

import lombok.RequiredArgsConstructor;
import lombok.Synchronized;

/**
 * Service for accessing stored weighing data.
 *
 * @author xsammak
 */
@Service
@RequiredArgsConstructor
public class WeighingStorageService {

    private static final String WEIGHING_DATA_FILE = "weighing_data.json";

    private final Jackson2ObjectMapperBuilder mapperBuilder;

    @Synchronized
    public void storeWeighingData(@Valid List<WeighingRecord> list) throws IOException {
        Files.write(getPath(), convertToJson(list).getBytes());
    }

    @Synchronized
    public List<WeighingRecord> getWeighingData() throws IOException {
        return convertToDto(Files.readAllBytes(getPath()));
    }

    private Path getPath() {
        return Paths.get(System.getProperty("user.dir"), WEIGHING_DATA_FILE);
    }

    private String convertToJson(List<WeighingRecord> measurements) throws JsonProcessingException {
        ObjectMapper mapper = mapperBuilder.build();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(measurements);
    }

    private List<WeighingRecord> convertToDto(byte[] bytes) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = mapperBuilder.build();
        return mapper.readValue(new String(bytes, StandardCharsets.UTF_8), new TypeReference<List<WeighingRecord>>() {});
    }
}
