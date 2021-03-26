package com.edson.teachercallroll.model;

import com.google.gson.annotations.SerializedName;

public class Teacher extends User {

    @SerializedName("id")
    private long id;

    public Teacher() {
    }

    public Teacher(long id_user, String email, int indentifier_number, String name, String last_name, String password, int status_user_id, long id_teacher) {
        super(id_user, email, indentifier_number, name, last_name, password, status_user_id);
        this.id = id_teacher;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
