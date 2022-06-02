package com.mediscreen.repoCentral.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.repoCentral.model.Patient;
import com.mediscreen.repoCentral.services.PatientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@TestPropertySource(locations= "classpath:application-test.properties")
public class CustomExceptionHandlerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PatientService patientService;


    @Test
    public void handlePatientAlreadyExistTest_shouldReturns_400() throws Exception{
        Patient patient = new Patient("TestNone", "test", new Date(System.currentTimeMillis())
                ,"F","addressTest","phoneTest");


        mvc.perform(post("/patient/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isCreated());


        mvc.perform(post("/patient/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error",hasSize(1)));
    }

    /*
    @Test
    public void handlePatientNotFound() throws Exception {
        long id = 1000;

        mvc.perform(get("/patient/getById")
                        .param("id",Long.toString(id))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", hasSize(1)));

    }
    */



}
