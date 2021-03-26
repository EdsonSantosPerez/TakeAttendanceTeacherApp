package com.edson.teachercallroll.adapterholder.formations;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edson.teachercallroll.R;
import com.edson.teachercallroll.model.FormationDto;

public class FormationHolder extends RecyclerView.ViewHolder {

    private TextView txtVFormationId;
    private TextView txtVFormationTitre;

    public FormationHolder(@NonNull View itemView) {
        super(itemView);
        txtVFormationId = itemView.findViewById(R.id.txtVId);
        txtVFormationTitre = itemView.findViewById(R.id.txtVTitre);
    }

    public void setDetails(FormationDto formation){
        txtVFormationId.setText(""+ formation.getId());
        txtVFormationTitre.setText(formation.getTitre_officiel());
    }
}
