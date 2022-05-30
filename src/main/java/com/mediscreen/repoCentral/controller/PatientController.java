package com.mediscreen.repoCentral.controller;

import com.mediscreen.repoCentral.Model.Patient;
import com.mediscreen.repoCentral.customExceptions.PatientAlreadyExistException;
import com.mediscreen.repoCentral.services.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
            patient =  patientService.getById(id);
        } catch (EntityNotFoundException e){
            logger.info("Error in /patient/getById :"+e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(patient, HttpStatus.OK);


    }
    @GetMapping ("/getPatientList")
    public ResponseEntity<List<Patient>> getPatient() {
        List<Patient> patientList = new ArrayList<>();
        try {
            patientList = patientService.getPatient();
        } catch (EntityNotFoundException e){
            logger.info("Error in /getPatient");
            e.getMessage();
        }
        return new ResponseEntity<>(patientList, HttpStatus.OK);
    }

    @GetMapping ("/getPatientByFamily")
    public List<Patient> getPatientByFamily (@RequestParam String family){
        List<Patient> patientls = null;
        try {
            patientls = patientService.getByFamily(family);
        } catch (NullPointerException e){
            e.getMessage();
        }
        return patientls ;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPatient (@RequestBody Patient patient){
        try {
            patientService.addAPatient(patient);
        } catch (PatientAlreadyExistException e){
            logger.info("Error in /patient/add :"+e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(patient, HttpStatus.ACCEPTED);
    }

    @PutMapping ("/update")
    public ResponseEntity<?> updatePatient (@RequestBody Patient patient){
        try {
            patientService.updatePatient(patient);
        } catch (EntityNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(patient, HttpStatus.ACCEPTED);
    }

}
