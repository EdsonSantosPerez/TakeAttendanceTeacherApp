package com.edson.teachercallroll.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edson.teachercallroll.R;
import com.edson.teachercallroll.adapterholder.assistancelist.AssistanceListAdapter;
import com.edson.teachercallroll.model.AssistanceSheetDto;
import com.edson.teachercallroll.viewmodel.AssistanceListViewModel;
import com.edson.teachercallroll.viewmodel.StudentsInSheetModifyViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AssistanceListActivity extends AppCompatActivity {

    private AssistanceListViewModel viewModel;
    private SharedPreferences shPref;
    private AssistanceListAdapter adapter;
    private ArrayList<AssistanceSheetDto> assistanceSheetDto;

    private RecyclerView rclvAssists;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistance_list);
        setupComponents();
        getAssistanceList();
    }

    public void setupComponents() {
        viewModel = new ViewModelProvider(AssistanceListActivity.this).get(AssistanceListViewModel.class);
        shPref = AssistanceListActivity.this.getSharedPreferences("TeacherCallRoll_ShPref", 0);
        rclvAssists = findViewById(R.id.rclvAssists);
        rclvAssists.setLayoutManager(new LinearLayoutManager(AssistanceListActivity.this));
    }

    public void getAssistanceList() {
        viewModel.getAssistanceList(shPref.getString("auth_token", null),
                Integer.parseInt(shPref.getString("identifier_number", null))).
                observe(AssistanceListActivity.this, (Observer<String>) jsonList -> {
                    if (viewModel.getHttpStatusCode() == 401 || viewModel.getHttpStatusCode() == 403) {
                        singOut();
                    } else {
                        if (jsonList != null) {
                            Type listAssistance = new TypeToken<ArrayList<AssistanceSheetDto>>() {
                            }.getType();
                            assistanceSheetDto = new Gson().fromJson(jsonList, listAssistance);
                            adapter = new AssistanceListAdapter(AssistanceListActivity.this, assistanceSheetDto);
                            rclvAssists.addItemDecoration(
                                    new DividerItemDecoration(AssistanceListActivity.this,
                                            LinearLayoutManager.VERTICAL));
                            adapter.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startStudentsInSheetModifyActivity(assistanceSheetDto.get(rclvAssists.getChildAdapterPosition(v)).getAssistanceSheetId());
                                }
                            });
                            rclvAssists.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    public void startStudentsInSheetModifyActivity(long assistanceSheetId) {
        intent = new Intent(AssistanceListActivity.this, StudentsInSheetModifyActivity.class);
        intent.putExtra("assistanceSheetId", assistanceSheetId);
        startActivity(intent);
    }

    public void singOut() {
        Toast.makeText(AssistanceListActivity.this, "Vous n'avez plus d’accès sur l'appli\nVeuillez vous reconnecter", Toast.LENGTH_LONG).show();
        SharedPreferences.Editor editor = shPref.edit();
        editor.clear();//delete all data from shared preferences
        editor.commit();//commit the changes
        intent = new Intent(AssistanceListActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}