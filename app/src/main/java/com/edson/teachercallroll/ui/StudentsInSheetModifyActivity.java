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
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import com.edson.teachercallroll.R;
import com.edson.teachercallroll.adapterholder.assistancelist.AssistanceListAdapter;
import com.edson.teachercallroll.adapterholder.studentsinsheet.AdapterSpinnerListener;
import com.edson.teachercallroll.adapterholder.studentsinsheet.StudentsInSheetAdapter;
import com.edson.teachercallroll.adapterholder.studentsinsheet.StudentsInSheetModifyAdapter;
import com.edson.teachercallroll.model.AssistanceSheetDto;
import com.edson.teachercallroll.model.StudentAssistance;
import com.edson.teachercallroll.viewmodel.StudentsInSheetModifyViewModel;
import com.edson.teachercallroll.viewmodel.StudentsInSheetViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class StudentsInSheetModifyActivity extends AppCompatActivity implements AdapterSpinnerListener {

    private StudentsInSheetModifyViewModel viewModel;
    private SharedPreferences shPref;
    private StudentsInSheetModifyAdapter adapter;
    private ArrayList<StudentAssistance> studentDtoList;
    private AssistanceSheetDto assistanceSheetDto;

    private StudentAssistance studentDeleted;
    private TextView txtVSheetGroupNameModify;
    private TextView txtVSheetModuleNameModify;
    private RecyclerView rclvStudentsInSheetModify;
    private FloatingActionButton floatingActionButtonModify;
    private String token;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_in_sheet_modify);
        setupComponents();
        getAssistanceList();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rclvStudentsInSheetModify);
    }

    public void setupComponents() {
        viewModel = new ViewModelProvider(StudentsInSheetModifyActivity.this).get(StudentsInSheetModifyViewModel.class);
        shPref = StudentsInSheetModifyActivity.this.getSharedPreferences("TeacherCallRoll_ShPref", 0);
        txtVSheetGroupNameModify = findViewById(R.id.txtVSheetGroupNameModify);
        txtVSheetModuleNameModify = findViewById(R.id.txtVSheetModuleNameModify);
        txtVSheetModuleNameModify.setSelected(true);
        rclvStudentsInSheetModify = findViewById(R.id.rclvStudentsInSheetModify);
        rclvStudentsInSheetModify.setLayoutManager(new LinearLayoutManager(StudentsInSheetModifyActivity.this));
        floatingActionButtonModify = findViewById(R.id.floatingActionButtonModify);
        token = shPref.getString("auth_token", null);
        intent = getIntent();
    }

    public void getAssistanceList() {
        viewModel.showAssistanceSheetDetails(token, intent.getLongExtra("assistanceSheetId", 0))
                .observe(StudentsInSheetModifyActivity.this, (Observer<String>) jsonList -> {
                    if (jsonList != null) {
                        Type listStudent = new TypeToken<AssistanceSheetDto>() {}.getType();
                        assistanceSheetDto = new Gson().fromJson(jsonList, listStudent);
                        txtVSheetModuleNameModify.setText(assistanceSheetDto.getModule());
                        txtVSheetGroupNameModify.setText(assistanceSheetDto.getGroupName());
                        studentDtoList = (ArrayList<StudentAssistance>) assistanceSheetDto.getStudents();
                        adapter = new StudentsInSheetModifyAdapter(StudentsInSheetModifyActivity.this, studentDtoList, String.valueOf(intent.getLongExtra("assistanceSheetId", 0)), this);
                        rclvStudentsInSheetModify.addItemDecoration(new DividerItemDecoration(StudentsInSheetModifyActivity.this, LinearLayoutManager.VERTICAL));
                        rclvStudentsInSheetModify.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onSpinnerChanged(String spinnerValue, String identifierNumber, int position) {
        viewModel.modifyStatus(token, String.valueOf(intent.getLongExtra("assistanceSheetId", 0)), identifierNumber, spinnerValue);
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
                    .observe(StudentsInSheetModifyActivity.this, (Observer<String>) reponse -> {
                        Toast.makeText(getApplicationContext(), reponse, Toast.LENGTH_LONG).show();
                        getAssistanceList();
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
        finish();
    }
}