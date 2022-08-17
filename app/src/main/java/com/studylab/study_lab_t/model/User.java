package com.studylab.study_lab_t.model;


public class User {
    private String name;
    private String id;
    private String password;
    private String phoneNumber;
    private String checkIn;

    public User(){}

    public User(String name, String userId, String password, String phoneNumber, String checkIn) {
        this.name = name;
        this.id = userId;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.checkIn = checkIn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }
}
