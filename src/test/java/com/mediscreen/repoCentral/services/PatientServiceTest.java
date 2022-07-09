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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations= "classpath:application-test.properties")
@ActiveProfiles("test")
@SpringBootTest
public class PatientServiceTest {

    Logger logger = LoggerFactory.getLogger(PatientServiceTest.class);

    @Autowired
    PatientRepo patientRepo;

    @Autowired
    PatientService patientService;
    Patient patient = new Patient(1L,"patientTestFirstname","patientTestLastName", new Date(System.currentTimeMillis()),
            "F", "initAddressTest", "initPhoneTest", null);


    @BeforeEach
    public void initTest (){
        this.patient = patientRepo.save(this.patient);
        logger.debug("patient repo size: "+patientRepo.findAll().size());

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

    /**
     *  assert PatientIdNotFoundException is thrown when id is not found
     */
    @Test
    public void shouldReturnPatientIdNotFoundExceptionIfIdNotFound(){

        PatientIdNotFoundException thrown = Assertions.assertThrows(PatientIdNotFoundException.class, ()->{
            patientService.getById(70L);
        });
    }


    @Test
    public void addPatientTest() throws PatientAlreadyExistException {
        Patient patient = new Patient(2L, "PatientTestToAdd","patientTestLastName", new Date(System.currentTimeMillis()),
                "F", "addressTest", "phoneTest", null);
        patientService.addAPatient(patient);

        assertNotNull(patientRepo.findById(patient.getId()));
    }

    /**
     * assert PatientAlreadyExistException is thrown when trying to add a patient already existing
     */
    @Test
    public void assertCanNotAddPatientIfExist (){

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
    public void getAllPatientByLastName() throws NullPointerException{
        String lastname = "TestNone";
        Patient patient = new Patient(new Date(System.currentTimeMillis())
                , "F", "addressTest", "phoneTest");
        patient.setLastName(lastname);
        Patient patientWithoutLastName = new Patient(new Date(System.currentTimeMillis())
                , "F", "addressTest", "phoneTest");


        patientRepo.save(patientWithoutLastName);
        patientRepo.save(patient);
        List<Patient> patientList = patientRepo.findAll();
        List<Patient> patientListSpecifiedLastName = patientService.getByLastName(lastname);

        assertNotEquals(patientList.size(),patientListSpecifiedLastName.size());
        assertTrue(patientListSpecifiedLastName.contains(patient));
    }

    @Transactional
    @Test
    public void updatePatientTest() throws PatientIdNotFoundException {
        Patient newPatient = new Patient(2L,"newPatientTest","patientTestLastName", new Date(System.currentTimeMillis()),
                "F", "newAddressTest", "newPhoneTest", null);

        Patient oldPatient = patientRepo.getReferenceById(this.patient.getId());
        logger.debug("id "+this.patient.getId());
        String name = oldPatient.getFirstName();
        logger.debug("name "+name);
        newPatient.setId(oldPatient.getId());
        patientService.updatePatient(newPatient);
        Optional<Patient> check = patientRepo.findById(this.patient.getId());
        assertNotEquals(name, check.get().getFirstName());

    }
}


