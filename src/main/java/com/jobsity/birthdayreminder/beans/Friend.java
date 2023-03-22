package com.jobsity.birthdayreminder.beans;

import java.time.LocalDate;

public class Friend {

    private Long id;
    private String lastName;
    private String firstName;
    private LocalDate dateOfBirth;
    private String email;
    private String phone;

    public Friend(Long id,String lastName, String firstName, LocalDate dateOfBirth, String email, String phone) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String birthdayGreetingMessageBuilder(){
        return "Happy Birthday, dear "+this.firstName;
    }

    public String getFullName(){
        return this.firstName+ " "+this.lastName;
    }
}
