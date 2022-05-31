package com.mediscreen.repoCentral.controller;

import com.mediscreen.repoCentral.customExceptions.PatientIdNotFoundException;
import com.mediscreen.repoCentral.model.Patient;
import com.mediscreen.repoCentral.customExceptions.PatientAlreadyExistException;
import com.mediscreen.repoCentral.services.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    PatientService patientService;


    @GetMapping ("/getById")
    public ResponseEntity<?> getPatientById (@RequestParam Long id){
        Patient patient = null;
        try {
            logger.info("in /getById");
            patient =  patientService.getById(id);
        } catch (PatientIdNotFoundException e){
            logger.info("Error in /patient/getById :"+e.getMessage());
           throw new PatientAlreadyExistException("Patient with id: "+id+" not found");
        }
        return new ResponseEntity<>(patient, HttpStatus.OK);


    }
    @GetMapping ("/getPatientList")
    public ResponseEntity<List<Patient>> getPatient() {
        List<Patient> patientList = patientService.getPatient();
        return new ResponseEntity<>(patientList, HttpStatus.OK);
    }

    @GetMapping ("/getPatientByFamily")
    public ResponseEntity< List<Patient>> getPatientByFamily (@RequestParam String family){
        List<Patient> patientls = patientService.getByFamily(family);
        return new ResponseEntity<>(patientls, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPatient (@RequestBody Patient patient){
        try {
            patientService.addAPatient(patient);
        } catch (PatientAlreadyExistException e){
            logger.info("Error in /patient/add :"+e.getMessage());
           throw new PatientAlreadyExistException("Patient "+patient.getName()+" already exist!");
        }
        return new ResponseEntity<>(patient, HttpStatus.ACCEPTED);
    }

    @PostMapping ("/update")
    public ResponseEntity<?> updatePatient (@RequestBody Patient patient){
        try {
            patientService.updatePatient(patient);
        } catch (PatientIdNotFoundException e){
            throw new PatientAlreadyExistException("Patient with id: "+patient.getId()+" not found");
        }
        return new ResponseEntity<>(patient, HttpStatus.ACCEPTED);
    }

}
