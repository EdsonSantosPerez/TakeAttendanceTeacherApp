package com.edson.teachercallroll.adapterholder.assistancelist;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edson.teachercallroll.R;
import com.edson.teachercallroll.model.AssistanceSheetDto;

import java.text.SimpleDateFormat;

public class AssistanceListHolder extends RecyclerView.ViewHolder {

    private TextView txtVModule;
    private TextView txtVSemestreGroup;
    private TextView txtVDateSheet;

    public AssistanceListHolder(@NonNull View itemView) {
        super(itemView);
        txtVModule = itemView.findViewById(R.id.txtVModule);
        txtVSemestreGroup = itemView.findViewById(R.id.txtVSemestreGroup);
        txtVDateSheet = itemView.findViewById(R.id.txtVDateSheet);
        txtVModule.setSelected(true);
    }

    public void setDetails(AssistanceSheetDto assistanceSheetDto) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy\n'à' HH'h'mm");
        txtVModule.setText(assistanceSheetDto.getModule());
        txtVSemestreGroup.setText("Semestre " + assistanceSheetDto.getSemestre() +
                " Groupe " + assistanceSheetDto.getGroupName());
        txtVDateSheet.setText(sdf.format(assistanceSheetDto.getStartDate()));
    }
}
