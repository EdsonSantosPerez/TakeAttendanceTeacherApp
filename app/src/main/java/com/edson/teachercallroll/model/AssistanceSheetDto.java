package com.edson.teachercallroll.model;

import java.util.Date;
import java.util.List;

public class AssistanceSheetDto {
    private long    assistanceSheetId;
    private String  assistanceSheetStatus;
    private String  teacherName;
    private Date    startDate;
    private Integer semestre;
    private String  groupName;
    private String module;
    private List<StudentAssistance> students;

    public AssistanceSheetDto(long assistanceSheetId, String assistanceSheetStatus, String teacherName, Date startDate, Integer semestre, String groupName, String module, List<StudentAssistance> students) {
        this.assistanceSheetId = assistanceSheetId;
        this.assistanceSheetStatus = assistanceSheetStatus;
        this.teacherName = teacherName;
        this.startDate = startDate;
        this.semestre = semestre;
        this.groupName = groupName;
        this.module = module;
        this.students = students;
    }

    public long getAssistanceSheetId() {
        return assistanceSheetId;
    }

    public void setAssistanceSheetId(long assistanceSheetId) {
        this.assistanceSheetId = assistanceSheetId;
    }

    public String getAssistanceSheetStatus() {
        return assistanceSheetStatus;
    }

    public void setAssistanceSheetStatus(String assistanceSheetStatus) {
        this.assistanceSheetStatus = assistanceSheetStatus;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public List<StudentAssistance> getStudents() {
        return students;
    }

    public void setStudents(List<StudentAssistance> students) {
        this.students = students;
    }
}

