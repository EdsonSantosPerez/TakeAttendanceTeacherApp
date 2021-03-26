package com.edson.teachercallroll.adapterholder.studentsinsheet;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edson.teachercallroll.R;
import com.edson.teachercallroll.model.StudentAssistance;

import java.util.ArrayList;

public class StudentsInSheetModifyAdapter extends RecyclerView.Adapter<StudentsInSheetModifyHolder> {

    private AdapterSpinnerListener adapterActionListener;

    private Context context;
    private ArrayList<StudentAssistance> studentAssistances;
    StudentsInSheetModifyHolder holder;
    String assistanceSheetId;


    public StudentsInSheetModifyAdapter(Context context, ArrayList<StudentAssistance> studentAssistances, String idSheet, AdapterSpinnerListener activity) {
        this.context = context;
        this.studentAssistances = studentAssistances;
        this.assistanceSheetId = idSheet;
        this.adapterActionListener = activity;
    }

    @NonNull
    @Override
    public StudentsInSheetModifyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row_students_in_sheet_modify, parent, false);
        return new StudentsInSheetModifyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentsInSheetModifyHolder holder, int position) {
        StudentAssistance studentAssistance = studentAssistances.get(position);
        holder.setDetails(studentAssistance, context, assistanceSheetId);

        holder.spnrStatusStudent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapterActionListener.onSpinnerChanged(parent.getItemAtPosition(position).toString(), studentAssistance.getIdentifierNumber(), position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return studentAssistances.size();
    }

}
