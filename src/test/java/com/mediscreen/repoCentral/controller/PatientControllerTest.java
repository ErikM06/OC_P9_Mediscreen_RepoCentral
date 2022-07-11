package com.mediscreen.repoCentral.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.repoCentral.model.Patient;
import com.mediscreen.repoCentral.services.PatientService;
import com.mediscreen.repoCentral.services.PatientServiceTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import static org.mockito.ArgumentMatchers.any;
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

    Logger logger = LoggerFactory.getLogger(PatientControllerTest.class);

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatientService patientService;


    @Test
    public void getPatientByIdTest_shouldReturns_200() throws Exception {
        mvc.perform(get("/patient/get-by-id"+"?id="+1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getPatientListTest_shouldReturns_200() throws Exception {
        String patientName = "Test";
        Patient patient = new Patient(1l,"patientName", "lastname" , new Date(System.currentTimeMillis()), "M", "addressTest", "phoneTest", null);

        List<Patient> patientLsTest = List.of(patient);
        given(patientService.getAllPatient()).willReturn(patientLsTest);



        mvc.perform(get("/patient/get-patient-list")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void getPatientByFamilyTest_shouldReturn_200() throws Exception {
        String lastname = "lastNameTest";

        Patient patient = new Patient(1L,"firstNameTest", lastname , new Date(System.currentTimeMillis()),
                "M", "addressTest", "phoneTest", null);


        List<Patient> patientLsTest = List.of(patient);
        given(patientService.getByLastName(any())).willReturn(patientLsTest);
        logger.debug(""+patientLsTest.get(0));

        mvc.perform(get("/patient/get-patient-by-lastname")
                        .param("lastname",objectMapper.writeValueAsString(lastname))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)));


    }
    @Test
    public void addPatientTest_shouldReturns_201() throws Exception {
        Patient patient = new Patient(new Date(System.currentTimeMillis())
                ,"F","addressTest","phoneTest");

        mvc.perform(post("/patient/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isCreated());
    }

    @Test
    public void updatePatientTest_shouldReturns_200() throws Exception {
        Patient patient = new Patient(new Date(System.currentTimeMillis())
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
        mvc.perform(get("/patient/delete-by-id")
                        .param("id", Long.toString(id)))
                .andExpect(status().isAccepted());
    }

}
