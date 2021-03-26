package com.edson.teachercallroll.apidata.request;

public class UpdateInfoRequest {

    private String identifierNumber;
    private String password;

    public UpdateInfoRequest() {
    }

    public UpdateInfoRequest(String identifierNumber, String password) {
        this.identifierNumber = identifierNumber;
        this.password = password;
    }

    public String getIdentifierNumber() {
        return identifierNumber;
    }

    public void setIdentifierNumber(String identifierNumber) {
        this.identifierNumber = identifierNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
