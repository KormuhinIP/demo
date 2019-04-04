package com.example.model;

import java.util.Date;

public class Teacher {

    private Long id;
    private String firstName, lastName, patronymic, numberLicense;

    private Date birthDay;

    private Integer experience;


    public Teacher() {
    }


    public Teacher(Long id, String firstName, String lastName, String patronymic, String numberLicense, Date birthDay, int experience) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.numberLicense = numberLicense;
        this.birthDay = birthDay;
        this.experience = experience;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getNumberLicense() {
        return numberLicense;
    }

    public void setNumberLicense(String numberLicense) {
        this.numberLicense = numberLicense;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }
}