package com.mediscreen.repoCentral.services;

import com.mediscreen.repoCentral.Model.Patient;
import com.mediscreen.repoCentral.customExceptions.PatientAlreadyExistException;
import com.mediscreen.repoCentral.repository.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    PatientRepo repo;

    /**
     *
     * @param patient
     * @return the patient to be saved
     * @throws PatientAlreadyExistException
     */
    public Patient addAPatient (Patient patient) throws PatientAlreadyExistException {
        if (repo.assertPatientExist(patient.getSex(), patient.getAddress(), patient.getPhone(), patient.getName())){
            throw  new PatientAlreadyExistException("Patient "+patient.getPhone()+" already exist");
        }
        return repo.save(patient);
    }

    public List<Patient> getPatient() throws EntityNotFoundException {
        return repo.findAll();
    }

    public List<Patient> getByFamily(String family) throws EntityNotFoundException{
        return repo.findByFamily(family);


    }
}
