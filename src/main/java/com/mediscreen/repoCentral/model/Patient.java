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
// see https://stackoverflow.com/questions/67353793/what-does-jsonignorepropertieshibernatelazyinitializer-handler-do
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpatient")
    private Long id;

    private String name;
    private String family;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dob;
    private String given;
    private String sex;
    private String address;
    private String phone;


    public Patient(String family, String given, Date dob, String sex, String address, String phone) {

        this.family = family;
        this.dob = dob;
        this.given = given;
        this.sex = sex;
        this.address = address;
        this.phone = phone;
    }

    public Patient(Long id, String name, String family, Date dob, String given, String sex, String address, String phone) {
        this.id = id;
        this.name = name;
        this.family = family;
        this.dob = dob;
        this.given = given;
        this.sex = sex;
        this.address = address;
        this.phone = phone;
    }

    public Patient() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGiven() {
        return given;
    }

    public void setGiven(String given) {
        this.given = given;
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
}
