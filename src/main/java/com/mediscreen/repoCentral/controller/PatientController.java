package com.mediscreen.repoCentral.controller;

import com.mediscreen.repoCentral.customExceptions.PatientAlreadyExistException;
import com.mediscreen.repoCentral.customExceptions.PatientIdNotFoundException;
import com.mediscreen.repoCentral.customExceptions.PatientLastNameNotFound;
import com.mediscreen.repoCentral.model.Patient;
import com.mediscreen.repoCentral.services.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    PatientService patientService;


    @GetMapping ("/get-by-id")
    public Patient getPatientById (@RequestParam Long id){
        Patient patient = null;
        try {
            logger.info("in /get-by-id");
            patient =  patientService.getById(id);
        } catch (PatientIdNotFoundException e){
            logger.info("Error in /patient/get-by-id :"+e.getMessage());
            throw new PatientIdNotFoundException("Patient with id: "+id+" not found");
        }
        return patient;


    }
    @GetMapping ("/get-patient-list")
    public List<Patient>getPatient() {
        List<Patient> patientList = patientService.getAllPatient();
        return patientList;
    }

    @GetMapping ("/get-patient-by-lastname")
    public List<Patient> getPatientByFamily (@RequestParam String lastname){
        List<Patient> patientls = null;

        try {
            patientls = patientService.getByLastName(lastname);
        } catch (PatientLastNameNotFound e){
            throw new PatientLastNameNotFound(e.getMessage());

        }
        return patientls;
    }

    @PostMapping("/add")
    public Patient addPatient (@RequestBody Patient patient, HttpServletResponse servletResponse){
        try {
            patientService.addAPatient(patient);
            servletResponse.setStatus(HttpServletResponse.SC_CREATED);
        } catch (PatientAlreadyExistException e){
            logger.info("Error in /patient/add :"+e.getMessage());
            throw new PatientAlreadyExistException(e.getMessage());
        }
        return patient;
    }

    @PostMapping ("/update")
    public Patient updatePatient (@RequestBody Patient patient, HttpServletResponse servletResponse){
        try {
            patientService.updatePatient(patient);
            servletResponse.setStatus(HttpServletResponse.SC_OK);

        } catch (PatientIdNotFoundException e){
            throw new PatientIdNotFoundException(e.getMessage());
        }
        return patient;
    }

    @GetMapping ("/delete-by-id")
    public void deletePatient (@RequestParam Long id, HttpServletResponse servletResponse){
        try {

            patientService.deletePatientById(id);
            servletResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
        } catch (PatientIdNotFoundException e){
            throw new PatientIdNotFoundException(e.getMessage());
        }
    }

}
