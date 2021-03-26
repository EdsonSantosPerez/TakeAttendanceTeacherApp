package com.edson.teachercallroll.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.edson.teachercallroll.R;
import com.edson.teachercallroll.viewmodel.SettingsViewModel;

public class SettingsActivity extends AppCompatActivity {

    private EditText edTxtName;
    private EditText edTxtStudentNum;
    private EditText edTxtUser;
    private EditText edTxtPassword;

    private SettingsViewModel viewModel;
    private SharedPreferences shPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setupComponents();
        getMyInfo();
    }

    public void setupComponents() {
        edTxtName = findViewById(R.id.edTxtName);
        edTxtStudentNum = findViewById(R.id.edTxtStudentNum);
        edTxtUser = findViewById(R.id.edTxtUser);
        edTxtPassword = findViewById(R.id.edTxtPassword);
        viewModel = new ViewModelProvider(SettingsActivity.this).get(SettingsViewModel.class);
        shPref = SettingsActivity.this.getSharedPreferences("TeacherCallRoll_ShPref", 0);
    }

    public void getMyInfo() {
        viewModel.getMyInformation(shPref.getString("auth_token", null),
                Integer.parseInt(shPref.getString("identifier_number", null))).
                observe(SettingsActivity.this, (Observer<String>) myInfo -> {
                    if(viewModel.getHttpStatusCode() == 401) {
                        Toast.makeText(SettingsActivity.this, "Vous n'avez plus d’accès sur l'appli\nVeuillez vous reconnecter", Toast.LENGTH_LONG).show();
                        singOut(this.getCurrentFocus());
                    }else {
                        if (myInfo != null) {//&& myInfo != ""){
                            edTxtName.setText(viewModel.getLastName() + " " + viewModel.getFirstName());
                            edTxtStudentNum.setText(viewModel.getIdentifierNumber());
                            edTxtUser.setText(viewModel.getEmail());
                        } else{
                            Toast.makeText(SettingsActivity.this, "Une erreur c'est produite !", Toast.LENGTH_LONG).show();
                        }
                    }

                });
    }

    public void updateMyInformation(View view){
        String identifierNumber = shPref.getString("identifier_number", null);
        String password = edTxtPassword.getText().toString();
        String token = shPref.getString("auth_token", null);
        viewModel.modifyPassword(token, identifierNumber, password).observe(SettingsActivity.this, (Observer<Integer>) newPassword -> {
                    if (newPassword  == 200 || newPassword  == 201){
                        Toast.makeText(SettingsActivity.this, "Mot de passe changé !", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(SettingsActivity.this, "Une erreur c'est produite !", Toast.LENGTH_LONG).show();
                    }
                });
    }


    public void singOut(View view){
        SharedPreferences.Editor editor = shPref.edit();
        editor.clear();//delete all data from shared preferences
        editor.apply();//commit the changes
        Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}