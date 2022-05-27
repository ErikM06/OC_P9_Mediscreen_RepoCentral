package com.mediscreen.repoCentral.repository;

import com.mediscreen.repoCentral.Model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepo extends JpaRepository<Patient,Long> {

    // query to check if patient already exist by some of his fields.
    @Query("SELECT CASE WHEN 'sex'=?1 AND 'address'=?2 AND 'phone'=?3 AND 'name'=?4 THEN 1 ELSE 0 END FROM Patient")
    Boolean assertPatientExist (String sex, String address, String phone, String name);

    
    @Query(value = "SELECT * FROM Patient WHERE family=?1")
    List<Patient> findByFamily(String family);
}
