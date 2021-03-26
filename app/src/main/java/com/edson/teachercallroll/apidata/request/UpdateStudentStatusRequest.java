package com.edson.teachercallroll.apidata.request;

public class UpdateStudentStatusRequest {
     private String assistanceSheetId;
     private String identifierNumber;
     private String status;

    public UpdateStudentStatusRequest() {
    }

    public UpdateStudentStatusRequest(String assistanceSheetId, String identifierNumber, String status) {
        this.assistanceSheetId = assistanceSheetId;
        this.identifierNumber = identifierNumber;
        this.status = status;
    }

    public String getAssistanceSheetId() {
        return assistanceSheetId;
    }

    public void setAssistanceSheetId(String assistanceSheetId) {
        this.assistanceSheetId = assistanceSheetId;
    }

    public String getIdentifierNumber() {
        return identifierNumber;
    }

    public void setIdentifierNumber(String identifierNumber) {
        this.identifierNumber = identifierNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
