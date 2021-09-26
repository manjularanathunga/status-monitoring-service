package com.kry.monitor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kry.monitor.entity.RequestUser;
import com.kry.monitor.service.RequestUserService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest() //webEnvironment = SpringBootTest.WebEnvironment.MOCK
@AutoConfigureMockMvc
class RequestUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RequestUserService requestUserService;

    private RequestUser requestUser;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void setUp() {
        requestUser = RequestUser.builder().userName("Admin").password("admin")
                .dateCreated(new Date()).userStatus(true).build();

    }

    @Test
    void saveUser() throws Exception {
        RequestUser userInput = RequestUser.builder().userName("abcd").password("ssss")
                .dateCreated(new Date()).userStatus(true).build();
        Mockito.when(requestUserService.saveUser(userInput)).thenReturn(requestUser);

        mockMvc.perform(post("/user").
                        contentType(MediaType.APPLICATION_JSON).content(asJsonString(userInput)))
                .andExpect(status().isCreated());
        //.andExpect(MockMvcResultMatchers.jsonPath("$.userID").exists());;
    }

    @Test
    void saveUserWithoutName() throws Exception {
        RequestUser userInput = RequestUser.builder().password("admin")
                .dateCreated(new Date()).userStatus(true).build();
        Mockito.when(requestUserService.saveUser(userInput)).thenReturn(requestUser);

        mockMvc.perform(post("/user").
                contentType(MediaType.APPLICATION_JSON).content(asJsonString(userInput))).andExpect(status().is(401));
    }

    @Test
    void fetchUserServices() throws Exception {
        mockMvc.perform(get("/user").
                contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}