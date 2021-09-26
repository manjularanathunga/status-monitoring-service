package com.kry.monitor.controller;

import com.kry.monitor.entity.RequestInfo;
import com.kry.monitor.service.RequestInfoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class RequestInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RequestInfoService requestInfoService;

    private RequestInfo requestInfo;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void fetchDashboardServiceById() {
    }

    @Test
    void fetchDashboardServiceByUserId() {
    }

    @Test
    void fetchServices() {
    }

    @Test
    void saveRequestInfo() {
    }

    @Test
    void fetchByRequestInfoServiceById() {
    }

    @Test
    void deleteRequestInfoService() {
    }

    @Test
    void updateRequestInfoService() {
    }

    @Test
    void fetchByRequestInfoServiceByName() {
    }

    @Test
    void fetchByRequestInfoServiceByNameByIgnoreCase() {
    }
}