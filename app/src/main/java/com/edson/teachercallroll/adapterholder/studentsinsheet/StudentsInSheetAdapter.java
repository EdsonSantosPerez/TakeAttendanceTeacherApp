package com.edson.teachercallroll.adapterholder.studentsinsheet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edson.teachercallroll.R;
import com.edson.teachercallroll.adapterholder.studentsinsheet.StudentsInSheetHolder;
import com.edson.teachercallroll.model.StudentAssistance;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class StudentsInSheetAdapter extends RecyclerView.Adapter<StudentsInSheetHolder> {

    private Context context;
    private ArrayList<StudentAssistance> studentDtoList;

    public StudentsInSheetAdapter(Context context, ArrayList<StudentAssistance> studentDtoList) {
        this.context = context;
        this.studentDtoList = studentDtoList;
    }

    public void setStudentList(ArrayList<StudentAssistance> studentDtoList){
        this.studentDtoList = studentDtoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentsInSheetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row_students_in_sheet, parent, false);
        return new StudentsInSheetHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentsInSheetHolder holder, int position) {
        StudentAssistance studentDto = studentDtoList.get(position);
        holder.setDetails(studentDto);
    }

    @Override
    public int getItemCount() {
        return studentDtoList.size();
    }
}
