package com.edson.teachercallroll.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FormationDto {
    @SerializedName("id")
    private String id;
    @SerializedName("titre_officiel")
    private String titre_officiel;
    @SerializedName("semestre")
    private ArrayList<SemestreDto> semestreDtoList;

    public FormationDto() {

    }

    public FormationDto(String id, String titre_officiel, ArrayList<SemestreDto> semestreDtoList) {
        this.id = id;
        this.titre_officiel = titre_officiel;
        this.semestreDtoList = semestreDtoList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitre_officiel() {
        return titre_officiel;
    }

    public void setTitre_officiel(String titre_officiel) {
        this.titre_officiel = titre_officiel;
    }

    public ArrayList<SemestreDto> getSemestreDtoList() {
        return semestreDtoList;
    }

    public void setSemestreDtoList(ArrayList<SemestreDto> semestreDtoList) {
        this.semestreDtoList = semestreDtoList;
    }
}
