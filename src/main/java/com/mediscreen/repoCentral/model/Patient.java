package com.mediscreen.repoCentral.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "patient")
@DynamicUpdate

public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpatient")
    private Long id;
    @Column (name = "firstname")
    private String firstname;
    @Column (name = "lastname")
    private String lastname;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dob;
    private String sex;
    private String address;
    private String phone;

    private String assessment;


    public Patient(Date dob, String sex, String address, String phone) {

        this.dob = dob;
        this.sex = sex;
        this.address = address;
        this.phone = phone;
    }

    public Patient(Long id, String firstname, String lastname, Date dob, String sex, String address, String phone, String assessment) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dob = dob;
        this.sex = sex;
        this.address = address;
        this.phone = phone;
        this.assessment = assessment;
    }

    public Patient() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String name) {
        this.firstname = name;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }
}
