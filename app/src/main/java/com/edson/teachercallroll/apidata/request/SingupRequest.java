package com.edson.teachercallroll.apidata.request;

public class SingupRequest {

    String email;
    String identifierNumber;
    String lastName;
    String name;
    String password;

    public SingupRequest() {
    }

    public SingupRequest(String email, String identifierNumber, String lastName, String name, String password) {
        this.email = email;
        this.identifierNumber = identifierNumber;
        this.lastName = lastName;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentifierNumber() {
        return identifierNumber;
    }

    public void setIdentifierNumber(String identifierNumber) {
        this.identifierNumber = identifierNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
