package com.mediscreen.repoCentral.controller;

import com.mediscreen.repoCentral.Model.Patient;
import com.mediscreen.repoCentral.customExceptions.PatientAlreadyExistException;
import com.mediscreen.repoCentral.services.PatientService;
import com.mediscreen.repoCentral.services.util.DateParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityNotFoundException;
import java.util.Date;

@Controller
@RequestMapping("/patient")
public class PatientController {

    Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    PatientService patientService;

    @Autowired
    DateParser dateParser;

    @GetMapping ("/getPatientList")
    public String getPatient(Model model) {
        try {
         model.addAttribute("patientList",patientService.getPatient());
        } catch (EntityNotFoundException e){
            logger.info("Error in /getPatient");
            e.getMessage();
        }
        return "patientList";
    }

    @GetMapping ("/getPatientByFamily")
    public String getPatientByFamily (Model model, @RequestParam String family){
        try {
            model.addAttribute("patientList",patientService.getByFamily(family));
        } catch (EntityNotFoundException e){
            e.getMessage();
        }
        return "patientList";
    }

    @PostMapping("/add")
    public ResponseEntity<Patient> addPatient (@RequestParam String family, String given, String dob, String sex, String address, String phone){
        Date dateOfBirth = dateParser.stringToDate(dob);
        Patient patient = new Patient(family,given,dateOfBirth,sex,address,phone);
        try {
            patientService.addAPatient(patient);
        } catch (PatientAlreadyExistException e) {
            logger.info("Error in /patient/add :"+e.getMessage());
            e.getMessage();
        }
        return new ResponseEntity<>(patient, HttpStatus.ACCEPTED);
    }

}
