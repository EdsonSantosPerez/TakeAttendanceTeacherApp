package com.edson.teachercallroll.model;

import com.google.gson.annotations.SerializedName;

public class GroupDto {
    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;

    public GroupDto() {
    }

    public GroupDto(String name, long  id) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
