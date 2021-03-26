package com.edson.teachercallroll.apidata.request;

public class CreateSheetRequest {
    private long moduleId;
    private long semestreId;
    private long groupId;

    public CreateSheetRequest() {

    }

    public CreateSheetRequest( long semestreId, long moduleId, long groupId) {
        this.moduleId = moduleId;
        this.semestreId = semestreId;
        this.groupId = groupId;
    }

    public long getModuleId() {
        return moduleId;
    }

    public void setModuleId(long moduleId) {
        this.moduleId = moduleId;
    }

    public long getSemestreId() {
        return semestreId;
    }

    public void setSemestreId(long semestreId) {
        this.semestreId = semestreId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }
}
