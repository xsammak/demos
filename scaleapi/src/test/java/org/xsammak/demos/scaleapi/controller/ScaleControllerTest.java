package org.xsammak.demos.scaleapi.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.xsammak.demos.scaleapi.ScaleApiApplication;
import org.xsammak.demos.scaleapi.model.dto.ScaleMeasurementSummaryDto;

import lombok.extern.slf4j.Slf4j;

/**
 * Test cases for the ScaleController class.
 *
 * @author xsammak
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ScaleApiApplication.class)
@AutoConfigureMockMvc(secure = false)
@ContextConfiguration
@Slf4j
public class ScaleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Value("classpath:json/test.json")
    private Resource testJsonFile;

    private ObjectMapper mapper;

    private String testJsonData;

    @PostConstruct
    public void initMappers() throws IOException {
        // Register an adapter to manage the date types as long values and vice versa
        mapper = new ObjectMapper().registerModules(new Jdk8Module(), new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        File file = testJsonFile.getFile();
        testJsonData = new String(Files.readAllBytes(file.toPath()));
    }

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * Test of importScaleMeasurementFile method, of class ScaleController.
     */
    @Test
    public void testImportScaleMeasurementFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "file", "application/json", testJsonData.getBytes());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/scales/import").file(file)).andExpect(status().is(200));
    }

    /**
     * Test of filterScaleMeasurements method, of class ScaleController.
     */
    @Test
    public void testFilterScaleMeasurements() throws Exception {
        String unit = "pound";
        String month = "June";
        MvcResult mvcResult = this.mockMvc.perform(get("/api/scales/summary/filter?month=" + month + "&unit=" + unit).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful()).andReturn();
        assertNotNull(mvcResult.getResponse().getContentAsString());
        ScaleMeasurementSummaryDto dto = mapper.readValue(mvcResult.getResponse().getContentAsString(), ScaleMeasurementSummaryDto.class);
    }
}
