package com.mediscreen.repoCentral.services;

import com.mediscreen.repoCentral.customExceptions.PatientAlreadyExistException;
import com.mediscreen.repoCentral.customExceptions.PatientIdNotFoundException;
import com.mediscreen.repoCentral.model.Patient;
import com.mediscreen.repoCentral.repository.PatientRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations= "classpath:application-test.properties")
@ActiveProfiles("test")
@SpringBootTest
public class PatientServiceTest {

    @Autowired
    PatientRepo patientRepo;

    @Autowired
    PatientService patientService;
    Patient patient = new Patient(1l,"patientTest","initTestNone", new Date(System.currentTimeMillis()), "initTest"
            , "F", "initAddressTest", "initPhoneTest");


    @BeforeEach
    public void initTest (){
        patientRepo.save(patient);
    }

    @AfterEach
    public void cleanTest (){
        patientRepo.deleteAll();
    }

    @Test
    public void getByIdTest() throws PatientIdNotFoundException {
       Patient patient = patientService.getById(this.patient.getId());
       assertNotNull(patient);
    }

    @Test
    public void shouldReturnExceptionIfIdNotFound (){
        // assert exception is thrown when id is not found
        PatientIdNotFoundException thrown = Assertions.assertThrows(PatientIdNotFoundException.class, ()->{
         patientService.getById(70L);
        });
    }

    @Test
    public void addPatientTest() throws PatientAlreadyExistException {
        Patient patient = new Patient("TestNone", "test", new Date(System.currentTimeMillis())
                , "F", "addressTest", "phoneTest");
        patient.setId(2L);
        patientService.addAPatient(patient);
        assertTrue(patientRepo.existsById(patient.getId()));
    }

    @Test
    public void assertCanNotAddPatientIfExist (){
        // add already existing patient
        PatientAlreadyExistException thrown = Assertions.assertThrows(PatientAlreadyExistException.class, ()->{
           patientService.addAPatient(patient);
        });


    }

    @Test
    public void getAllPatientTest(){
        List<Patient> patientList = patientService.getAllPatient();
        assertFalse(patientList.isEmpty());
    }
    @Transactional
    @Test
    public void getAllPatientByFamily() throws NullPointerException{
        String family = "TestNone";
        Patient patient = new Patient(family, "test", new Date(System.currentTimeMillis())
                , "F", "addressTest", "phoneTest");
        patientRepo.save(patient);
        List<Patient> patientList = patientRepo.findAll();
        List<Patient> patientListSpecifiedFamily = patientService.getByFamily(family);

        assertNotEquals(patientList.size(),patientListSpecifiedFamily.size());
        assertTrue(patientListSpecifiedFamily.contains(patient));


    }
    @Transactional
    @Test
    public void updatePatientTest() throws PatientIdNotFoundException {
        Patient newPatient = new Patient(1L,"newPatientTest","TestDanger", new Date(System.currentTimeMillis()), "newTest"
                , "F", "newAddressTest", "newPhoneTest");

       Patient oldPatient = patientRepo.getReferenceById(1L);
       patientService.updatePatient(newPatient);
       assertEquals(patientRepo.getReferenceById(oldPatient.getId()).getFamily(),newPatient.getFamily());
    }
}


