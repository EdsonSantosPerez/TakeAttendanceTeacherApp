package com.edson.teachercallroll.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.edson.teachercallroll.R;
import com.edson.teachercallroll.adapterholder.studentsinsheet.StudentsInSheetAdapter;
import com.edson.teachercallroll.model.AssistanceSheetDto;
import com.edson.teachercallroll.model.StudentAssistance;
import com.edson.teachercallroll.viewmodel.StudentsInSheetViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class StudentsInSheetActivity extends AppCompatActivity {

    private TextView txtVSheetTeacherName;
    private TextView txtVSheetModuleName;
    private TextView txtVSheetGroupName;
    private RecyclerView rclvStudentsInSheet;
    private StudentsInSheetViewModel viewModel;

    private StudentsInSheetAdapter adapter;
    private AssistanceSheetDto assistanceSheetDto;
    private ArrayList<StudentAssistance> studentDtoList;
    private SharedPreferences shPref;
    private StudentAssistance studentDeleted;
    private String token;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_in_sheet);
        try {
            setupComponents();
            getStudentsInAssistanceSheet();
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
            itemTouchHelper.attachToRecyclerView(rclvStudentsInSheet);
            txtVSheetTeacherName.setText(shPref.getString("last_name", "") + " " + shPref.getString("first_name", ""));
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    public void setupComponents() {
        viewModel = new ViewModelProvider(StudentsInSheetActivity.this).get(StudentsInSheetViewModel.class);
        shPref = StudentsInSheetActivity.this.getSharedPreferences("TeacherCallRoll_ShPref", 0);
        txtVSheetTeacherName = findViewById(R.id.txtVSheetTeacherName);
        txtVSheetModuleName = findViewById(R.id.txtVSheetModuleName);
        txtVSheetModuleName.setSelected(true);
        txtVSheetGroupName = findViewById(R.id.txtVSheetGroupName);
        rclvStudentsInSheet = findViewById(R.id.rclvStudentsInSheet);
        rclvStudentsInSheet.setLayoutManager(new LinearLayoutManager(StudentsInSheetActivity.this));
        token = shPref.getString("auth_token", null);
        intent = getIntent();
    }

    public void getStudentsInAssistanceSheet() {
        viewModel.showAssistanceSheetDetails(token, intent.getLongExtra("idSheet", 0))
                .observe(StudentsInSheetActivity.this, (Observer<String>) jsonList -> {
            if (jsonList != null) {
                Type listStudent = new TypeToken<AssistanceSheetDto>() {}.getType();
                assistanceSheetDto = new Gson().fromJson(jsonList, listStudent);
                txtVSheetModuleName.setText(assistanceSheetDto.getModule());
                txtVSheetGroupName.setText(assistanceSheetDto.getGroupName());
                studentDtoList = (ArrayList<StudentAssistance>) assistanceSheetDto.getStudents();
                adapter = new StudentsInSheetAdapter(StudentsInSheetActivity.this, studentDtoList);
                rclvStudentsInSheet.addItemDecoration(new DividerItemDecoration(StudentsInSheetActivity.this, LinearLayoutManager.VERTICAL));
                rclvStudentsInSheet.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            //get the position in the list from the viewHolder.
            int position = viewHolder.getAdapterPosition();
            //get the StudentDto object.
            studentDeleted = studentDtoList.get(position);
            //notify of change to recycler view
            adapter.notifyItemRemoved(position);
            //delete request
            viewModel.deleteStudentFromSheet(token, assistanceSheetDto.getAssistanceSheetId(), Integer.parseInt(studentDeleted.getIdentifierNumber()))
                    .observe(StudentsInSheetActivity.this, (Observer<String>) reponse -> {
                        Toast.makeText(getApplicationContext(), reponse, Toast.LENGTH_LONG).show();
                        getStudentsInAssistanceSheet();
                    });
//            Snackbar.make(rclvStudentsInSheet, studentDeleted.getIdentifierNumber() + " " + studentDeleted.getLastName() + " " + studentDeleted.getFirstName(), Snackbar.LENGTH_LONG)
//                    .setAction("Undo", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                        }
//                    }).show();
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.design_default_color_error))
                    .addActionIcon(R.drawable.ic_delete_sweep_light)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    public void backHomeActivity(View view) {
        Toast.makeText(view.getContext(), "Feuille Terminée", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(StudentsInSheetActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void startReadQrCodeActivity(View view) {
        Intent intent = new Intent(StudentsInSheetActivity.this, ReadQrCodeActivity.class);
        intent.putExtra("idSheet", assistanceSheetDto.getAssistanceSheetId());
        startActivity(intent);
    }

}