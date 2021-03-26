package com.edson.teachercallroll.apidata.request;

public class UpdateModuleGroupRequest {
    private long assistanceSheetId;
    private long idGroup;
    private long idModule;

    public UpdateModuleGroupRequest() {
    }

    public UpdateModuleGroupRequest(long assistanceSheetId, long idGroup, long idModule) {
        this.assistanceSheetId = assistanceSheetId;
        this.idGroup = idGroup;
        this.idModule = idModule;
    }

    public long getAssistanceSheetId() {
        return assistanceSheetId;
    }

    public void setAssistanceSheetId(long assistanceSheetId) {
        this.assistanceSheetId = assistanceSheetId;
    }

    public long getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(long idGroup) {
        this.idGroup = idGroup;
    }

    public long getIdModule() {
        return idModule;
    }

    public void setIdModule(long idModule) {
        this.idModule = idModule;
    }
}
