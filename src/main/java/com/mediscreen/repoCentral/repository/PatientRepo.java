package com.mediscreen.repoCentral.repository;

import com.mediscreen.repoCentral.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepo extends JpaRepository<Patient,Long> {

    // query to check if patient already exist by some of his fields.
    @Query("SELECT CASE WHEN EXISTS (SELECT p FROM Patient p WHERE p.sex=:sex AND p.address=:address AND p.phone=:phone )" +
            "THEN true ELSE false END FROM Patient")
    Boolean assertPatientExist (String sex, String address, String phone);

    // find a patient by his family (From TestNone to TestInDanger)
    @Query(value = "SELECT p FROM Patient p WHERE p.lastname=?1")
    List<Patient> findByLastName(String lastname);
}
