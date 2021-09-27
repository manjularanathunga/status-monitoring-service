package com.kry.monitor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kry.monitor.entity.RequestUser;
import com.kry.monitor.service.RequestUserService;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
class RequestUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RequestUserService requestUserService;


    private RequestUser requestUser;


    @BeforeEach
    void setUp() {
        requestUser = RequestUser.builder().userName("Admin").password("admin")
                .dateCreated(new Date()).userStatus(true).build();

    }

    @SneakyThrows
    @Test
    void saveUser() {
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
                contentType(MediaType.APPLICATION_JSON).content(asJsonString(userInput))).andExpect(status().is(400));
    }


    @Test
    void fetchByUserIdNotExistingID() throws Exception {
        mockMvc.perform(get("/user/100"))
                .andExpect(status().isNoContent());
    }

    @Test
    void fetchUserServices() throws Exception {
        mockMvc.perform(get("/users").
                contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void deleteUserById() throws Exception {
        mockMvc.perform(delete("/user/100"))
                .andExpect(status().isOk());
    }

    @Test
    void fetch_by_not_existing_username() throws Exception {
        mockMvc.perform(get("/user/name/user"))
                .andExpect(status().isNoContent());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}