package com.edson.teachercallroll.model;

import com.google.gson.annotations.SerializedName;

public class ModuleDto {
    @SerializedName("id")
    private long id;
    @SerializedName("titre")
    private String titre;

    public ModuleDto() {
    }

    public ModuleDto(long id, String titre) {
        this.id= id;
        this.titre = titre;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
}
