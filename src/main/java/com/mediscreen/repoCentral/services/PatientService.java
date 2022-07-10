package com.mediscreen.repoCentral.services;

import com.mediscreen.repoCentral.customExceptions.PatientAlreadyExistException;
import com.mediscreen.repoCentral.customExceptions.PatientIdNotFoundException;
import com.mediscreen.repoCentral.customExceptions.PatientLastNameNotFound;
import com.mediscreen.repoCentral.model.Patient;
import com.mediscreen.repoCentral.repository.PatientRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    Logger logger = LoggerFactory.getLogger(PatientService.class);

    @Autowired
    PatientRepo repo;

    /**
     * @param patient
     * @return the patient to be saved
     * @throws PatientAlreadyExistException if patient is already in db
     */
    public Patient addAPatient (Patient patient) throws PatientAlreadyExistException {
        logger.info("in PatientService addAPatient");
        if (repo.assertPatientExistByLastNameAndFirstName(patient.getLastName(), patient.getFirstName())){
            logger.info("patient already exist in PatientService addAPatient");
            throw  new PatientAlreadyExistException("Patient "+patient.getLastName()+" already exist");
        }
        return repo.save(patient);
    }

    /**
     * @return a list of all patients
     */
    public List<Patient> getAllPatient() {
        logger.info("in PatientService getPatient");
        return repo.findAll();
    }

    /**
     *
     * @param lastName
     * @return a list of all patient with the given lastName
     * @throws NullPointerException if none
     */
    public List<Patient> getByLastName(String lastName) throws PatientLastNameNotFound {
        List<Patient> patientLs = new ArrayList<>();
        // check if lastName exist
       if (!repo.assertPatientExistByLastName(lastName)){
           throw new PatientLastNameNotFound("Patient with lastname "+ lastName+" not found!");
       }
            patientLs = repo.findByLastName(lastName);


        logger.info("in patientService getByLastName");
        return patientLs;
    }

    /**
     *
     * @param patient to update
     * @throws PatientIdNotFoundException
     */
    public void updatePatient (Patient patient) throws PatientIdNotFoundException {
        logger.info("in PatientService updatePatient");
        if (!repo.existsById(patient.getId())){
            throw  new PatientIdNotFoundException("patient with id "+patient.getId()+ " not found !");
        }
        repo.save(patient);
    }

    /**
     *
     * @param id
     * @return long id
     * @throws PatientIdNotFoundException
     */
    public Patient getById(Long id) throws PatientIdNotFoundException{
        if (!repo.existsById(id)){
            throw  new PatientIdNotFoundException("patient with id "+ id+ " not found !");
        }
        return repo.getReferenceById(id);
    }

    public void deletePatientById(Long id) throws PatientIdNotFoundException {
        if (!repo.existsById(id)){
            throw new PatientIdNotFoundException("Patient with id "+ id+ " not found !");
        }
        repo.deleteById(id);
    }
}
