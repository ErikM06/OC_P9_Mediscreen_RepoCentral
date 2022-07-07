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


import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PatientController.class)
@TestPropertySource(locations= "classpath:application-test.properties")
public class PatientControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatientService patientService;


    @Test
    public void getPatientByIdTest_shouldReturns_200() throws Exception {
        mvc.perform(get("/patient/getById"+"?id="+1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getPatientListTest_shouldReturns_200() throws Exception {
        Patient patient = new Patient();
        String patientName = "Test";
        patient.setFirstname(patientName);
        List<Patient> patientLsTest = Arrays.asList(patient);
        given(patientService.getAllPatient()).willReturn(patientLsTest);

        mvc.perform(get("/patient/getPatientList")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(patientName)));
    }

    @Test
    public void getPatientByFamilyTest_shouldReturn_200() throws Exception {
        Patient patient = new Patient();
        String family = "TestNone";
        patient.setFamily(family);

        List<Patient> patientLsTest = Arrays.asList(patient);
        given(patientService.getByFamily(family)).willReturn(patientLsTest);

        mvc.perform(get("/patient/getPatientByFamily")
                        .param("family",family)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].family", is(family)));

    }
    @Test
    public void addPatientTest_shouldReturns_201() throws Exception {
        Patient patient = new Patient("TestNone", "test", new Date(System.currentTimeMillis())
                ,"F","addressTest","phoneTest");

        mvc.perform(post("/patient/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isCreated());
    }

    @Test
    public void updatePatientTest_shouldReturns_200() throws Exception {
        Patient patient = new Patient("TestNone", "test", new Date(System.currentTimeMillis())
                ,"F","addressTest","phoneTest");
        patient.setId(1L);

        mvc.perform(post("/patient/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isOk());
    }

    @Test
    public void deletePatientTest_shouldReturns_202() throws Exception {
        long id = 1;
        mvc.perform(delete("/patient/deleteById")
                        .param("id", Long.toString(id)))
                .andExpect(status().isAccepted());
    }

}
