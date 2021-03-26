package com.edson.teachercallroll.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.edson.teachercallroll.R;

public class HomeActivity extends AppCompatActivity {

    TextView txtVLastName;
    TextView txtVFirstName;
    CardView crdVGenerateSheet;
    CardView crdVManageSheet;
    CardView crdVCreateTeacher;
    CardView crdVSettings;
    SharedPreferences shPref;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupComponents();
        setCompleteName();
    }

    private void setupComponents() {
        shPref = HomeActivity.this.getSharedPreferences("TeacherCallRoll_ShPref", 0);
        userValidation();
        txtVLastName = findViewById(R.id.txtVLastName);
        txtVFirstName = findViewById(R.id.txtVFirstName);
        crdVGenerateSheet = findViewById(R.id.crdVGenerateSheet);
        crdVManageSheet = findViewById(R.id.crdVManageSheet);
        crdVCreateTeacher = findViewById(R.id.crdVCreateTeacher);
        crdVSettings = findViewById(R.id.crdVSettings);
    }

    public void userValidation() {
        try {
            String token = shPref.getString("auth_token", null);
            if (token == null) {
                intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        } catch (NullPointerException npe) {

        }
    }

    public void startAssistsActivity(View view) {
        intent = new Intent(view.getContext(), AssistanceListActivity.class);
        startActivity(intent);
    }

    public void startSelectGroupActivity(View view) {
        intent = new Intent(view.getContext(), SelectGroupActivity.class);
        startActivity(intent);
    }

    public void startStudentsInSheetsActivity(View view) {
        intent = new Intent(view.getContext(), StudentsInSheetActivity.class);
        startActivity(intent);
    }

    public void startTeacherSingupActivity(View view) {
        intent = new Intent(view.getContext(), TeacherSingupActivity.class);
        startActivity(intent);
    }

    public void startSettingsActivity(View view) {
        intent = new Intent(view.getContext(), SettingsActivity.class);
        startActivity(intent);
    }

    public void setCompleteName() {
        txtVLastName.setText(shPref.getString("last_name", null));
        txtVFirstName.setText(shPref.getString("first_name", null));
    }
}