package com.kry.monitor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kry.monitor.entity.RequestInfo;
import com.kry.monitor.entity.RequestUser;
import com.kry.monitor.service.RequestInfoService;
import com.kry.monitor.service.RequestUserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
class RequestInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RequestInfoService requestInfoService;


    private RequestInfo requestInfo;

    @BeforeEach
    void setUp() {
        requestInfo = RequestInfo.builder().serviceName("Patient Service")
                .serviceStatus("PASS").creationTime(new Date()).monitorUserId(1L).status(true)
                .requestUrl("http://localhost:8083/kry").creationTime(new Date()).status(true).build();
    }

    @SneakyThrows
    @Test
    void fetchServices() {
        mockMvc.perform(get("/service/dashboard").
                contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void fetch_service_by_not_exisit() {
        mockMvc.perform(get("/service/10000"))
                .andExpect(status().isNoContent());
    }

    @SneakyThrows
    @Test
    void saveRequestInfo() {
        RequestInfo inputRequest = RequestInfo.builder().serviceName("Patient Service")
                .serviceStatus("PASS").creationTime(new Date()).monitorUserId(1L).status(true)
                .requestUrl("http://localhost:8083/kry").creationTime(new Date()).status(true).build();

        Mockito.when(requestInfoService.saveRequestInfo(inputRequest)).thenReturn(requestInfo);

        mockMvc.perform(post("/service").
                        contentType(MediaType.APPLICATION_JSON).content(asJsonString(requestInfo)))
                .andExpect(status().isCreated());
    }

    @SneakyThrows
    @Test
    void deleteRequestInfoService() {
        mockMvc.perform(get("/servicedel/500"))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void updateRequestInfoService() {

        RequestInfo inputRequest = RequestInfo.builder().serviceID(1L).serviceName("Patient Service Updated")
                .serviceStatus("FAIL").creationTime(new Date()).monitorUserId(1L).status(true)
                .requestUrl("http://localhost:8083/Updated_service").creationTime(new Date()).status(true).build();

        Mockito.when(requestInfoService.saveRequestInfo(inputRequest)).thenReturn(requestInfo);

        mockMvc.perform(post("/service").
                        contentType(MediaType.APPLICATION_JSON).content(asJsonString(requestInfo)))
                .andExpect(status().isCreated());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}