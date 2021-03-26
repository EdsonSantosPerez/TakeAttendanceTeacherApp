package com.edson.teachercallroll.adapterholder.studentsinsheet;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edson.teachercallroll.R;
import com.edson.teachercallroll.model.StudentAssistance;
import com.edson.teachercallroll.model.StudentDto;

import java.text.SimpleDateFormat;

public class StudentsInSheetHolder extends RecyclerView.ViewHolder {

    private TextView txtVName;
    private TextView txtVDate;
    private TextView txtVIdentifierNum;


    public StudentsInSheetHolder(@NonNull View itemView) {
        super(itemView);
        txtVName = itemView.findViewById(R.id.txtVName);
        txtVDate = itemView.findViewById(R.id.txtVDate);
        txtVIdentifierNum = itemView.findViewById(R.id.txtVIdentifierNum);
    }

    public void setDetails(StudentAssistance studentDTO) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        txtVName.setText(studentDTO.getLastName() + " " + studentDTO.getFirstName());
        txtVDate.setText(sdf.format(studentDTO.getDate()));
        txtVIdentifierNum.setText(studentDTO.getIdentifierNumber());
    }
}
