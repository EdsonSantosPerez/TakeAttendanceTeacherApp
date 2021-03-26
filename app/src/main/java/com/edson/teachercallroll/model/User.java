package com.edson.teachercallroll.model;

import com.google.gson.annotations.SerializedName;

public abstract class User {

    @SerializedName("id")
    private long id;
    @SerializedName("email")
    private String email;
    @SerializedName("indentifier_number")
    private int indentifier_number;
    @SerializedName("name")
    private String name;
    @SerializedName("last_name")
    private String last_name;
    @SerializedName("password")
    private String password;
    @SerializedName("status_user_id")
    private int status_user_id;

    public User() {
    }

    public User(long id, String email, int indentifier_number, String name, String last_name, String password, int status_user_id) {
        this.id = id;
        this.email = email;
        this.indentifier_number = indentifier_number;
        this.name = name;
        this.last_name = last_name;
        this.password = password;
        this.status_user_id = status_user_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIndentifier_number() {
        return indentifier_number;
    }

    public void setIndentifier_number(int indentifier_number) {
        this.indentifier_number = indentifier_number;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus_user_id() {
        return status_user_id;
    }

    public void setStatus_user_id(int status_user_id) {
        this.status_user_id = status_user_id;
    }
}
