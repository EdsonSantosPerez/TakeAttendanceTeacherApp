package com.edson.teachercallroll.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class StudentDto {

    @SerializedName("id")
    private long id;
    @SerializedName("date")
    private String date;
    @SerializedName("firstName")
    private String name;
    @SerializedName("lastName")
    private String last_name;
    @SerializedName("indentifierNumber")
    private int indentifier_number;
    @SerializedName("indentifierNumbr")
    private String status;

    public StudentDto() {
    }

    public StudentDto(long id, String date, String name, String last_name, int indentifier_number, String status) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.last_name = last_name;
        this.indentifier_number = indentifier_number;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getIndentifier_number() {
        return indentifier_number;
    }

    public void setIndentifier_number(int indentifier_number) {
        this.indentifier_number = indentifier_number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
