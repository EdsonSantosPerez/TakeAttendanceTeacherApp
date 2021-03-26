package com.edson.teachercallroll.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.edson.teachercallroll.R;
import com.edson.teachercallroll.viewmodel.TeacherSingupViewModel;

public class TeacherSingupActivity extends AppCompatActivity {

    private EditText etxtLastName;
    private EditText etxtFirstName;
    private EditText etxtStudentNum;
    private EditText etxtUser;
    private EditText etxtPassword;
    private Button btnSingup;

    private TeacherSingupViewModel viewModel;
    SharedPreferences shPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_singup);
        setupComponents();
    }

    public void setupComponents() {
        etxtLastName = findViewById(R.id.etxtLastName);
        etxtFirstName = findViewById(R.id.etxtFirstName);
        etxtStudentNum = findViewById(R.id.etxtStudentNum);
        etxtUser = findViewById(R.id.etxtUser);
        etxtPassword = findViewById(R.id.etxtPassword);
        btnSingup = findViewById(R.id.btnSingup);
        viewModel = new ViewModelProvider(TeacherSingupActivity.this).get(TeacherSingupViewModel.class);
        shPref = TeacherSingupActivity.this.getSharedPreferences("TeacherCallRoll_ShPref", 0);
    }

    public void singupTeacher(View view) {
        Log.i("TAG", "singupTeacher: here");
        viewModel.singupTeacher(
                shPref.getString("auth_token", null),
                etxtUser.getText().toString(), etxtStudentNum.getText().toString(),
                etxtLastName.getText().toString(), etxtFirstName.getText().toString(),
                etxtPassword.getText().toString()
        ).observe(TeacherSingupActivity.this,
                (Observer<String>) message -> {
                    if (viewModel.getHttpStatusCode() == 401) {
                        singOut();
                    }
                    Log.i("TAG", "singupTeacher: " + message);
                    Toast.makeText(TeacherSingupActivity.this, message, Toast.LENGTH_LONG).show();
                });
    }

    public void singOut() {
        Toast.makeText(TeacherSingupActivity.this, "Vous n'avez plus d’accès sur l'appli\nVeuillez vous reconnecter", Toast.LENGTH_LONG).show();
        SharedPreferences.Editor editor = shPref.edit();
        editor.clear();//delete all data from shared preferences
        editor.commit();//commit the changes
        Intent intent = new Intent(TeacherSingupActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



}