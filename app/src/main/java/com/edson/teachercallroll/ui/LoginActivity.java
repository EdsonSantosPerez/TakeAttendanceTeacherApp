package com.edson.teachercallroll.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.edson.teachercallroll.R;
import com.edson.teachercallroll.databinding.ActivityLoginBinding;
import com.edson.teachercallroll.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupComponents();
    }

    public void setupComponents(){
        viewModel = new ViewModelProvider(LoginActivity.this).get(LoginViewModel.class);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        binding.setLoginViewModel(viewModel);
    }

    public void startHome(View v) {
        try {
            viewModel.userLoginObserver().observe(LoginActivity.this, (Observer<String>) message -> {
                String token = viewModel.getToken();
                Toast.makeText(LoginActivity.this, viewModel.message.getValue(), Toast.LENGTH_LONG).show();
                if (token != null) {
                    //shared preferences save token
                    SharedPreferences shPref = getApplicationContext().getSharedPreferences("TeacherCallRoll_ShPref", 0);
                    SharedPreferences.Editor editor = shPref.edit();
                    editor.putString("auth_token", "Bearer " + token);
                    Log.i("LOGIN_ACTIVITY", token);
                    editor.putString("last_name", viewModel.getLastName());
                    editor.putString("first_name", viewModel.getFirstName());
                    editor.putString("identifier_number", viewModel.getIdentifierNumber());
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            });
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

}