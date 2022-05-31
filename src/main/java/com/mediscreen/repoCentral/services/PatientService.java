package com.mediscreen.repoCentral.services;

import com.mediscreen.repoCentral.customExceptions.PatientIdNotFoundException;
import com.mediscreen.repoCentral.model.Patient;

import com.mediscreen.repoCentral.customExceptions.PatientAlreadyExistException;
import com.mediscreen.repoCentral.repository.PatientRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    Logger logger = LoggerFactory.getLogger(PatientService.class);

    @Autowired
    PatientRepo repo;

    /**
     *
     * @param patient
     * @return the patient to be saved
     * @throws PatientAlreadyExistException
     */
    public Patient addAPatient (Patient patient) throws PatientAlreadyExistException {
        logger.info("in PatientService addAPatient");
        if (repo.assertPatientExist(patient.getSex(), patient.getAddress(), patient.getPhone())){
            logger.info("patient already exist in PatientService addAPatient");
            throw  new PatientAlreadyExistException("Patient "+patient.getPhone()+" already exist");
        }
        return repo.save(patient);
    }

    /**
     *
     * @return a list of all patients
     * @throws EntityNotFoundException
     */
    public List<Patient> getPatient() throws PatientIdNotFoundException {
        logger.info("in PatientService getPatient");
        return repo.findAll();
    }

    /**
     *
     * @param family
     * @return a list of all patient with the given family
     * @throws NullPointerException
     */
    public List<Patient> getByFamily(String family) throws  NullPointerException {
        List<Patient> patientLs = new ArrayList<>();
        // check if family type existe
        try {
            patientLs = repo.findByFamily(family);
        } catch (NullPointerException e){
            logger.info("Error in patientService getByFamily : NullPointerException");
            e.getMessage();
        }
        logger.info("in patientService getByFamily");
        return patientLs;
    }


    public void updatePatient (Patient patient) throws PatientIdNotFoundException {
        logger.info("in PatientService updatePatient");
        if (!repo.existsById(patient.getId())){
            throw  new PatientIdNotFoundException("patient with id "+patient.getId()+ " not found !");
        }


        repo.save(patient);
    }

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
