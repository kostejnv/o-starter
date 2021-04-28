package com.example.o_starter;

public class CompetitiorsBase {

    private String firstName;
    private String lastName;
    private int startTime;
    private String registrationId;

    public CompetitiorsBase(String firstName, String lastName, int startTime, String registrationId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.startTime = startTime;
        this.registrationId = registrationId;
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

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }
}
