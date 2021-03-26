package com.edson.teachercallroll.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SemestreDto {
    @SerializedName("id")
    private long id;
    @SerializedName("semestre")
    private String semestre;
    @SerializedName("group")
    private ArrayList<GroupDto> groupDtoList;
    @SerializedName("module")
    private ArrayList<ModuleDto> moduleDtoList;

    public SemestreDto() {
    }

    public SemestreDto(long id, String semestre, ArrayList<GroupDto> groupDtoList, ArrayList<ModuleDto> moduleDtoList) {
        this.id = id;
        this.semestre = semestre;
        this.groupDtoList = groupDtoList;
        this.moduleDtoList = moduleDtoList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public ArrayList<GroupDto> getGroupDtoList() {
        return groupDtoList;
    }

    public void setGroupDtoList(ArrayList<GroupDto> groupDtoList) {
        this.groupDtoList = groupDtoList;
    }

    public ArrayList<ModuleDto> getModuleDtoList() {
        return moduleDtoList;
    }

    public void setModuleDtoList(ArrayList<ModuleDto> moduleDtoList) {
        this.moduleDtoList = moduleDtoList;
    }
}
