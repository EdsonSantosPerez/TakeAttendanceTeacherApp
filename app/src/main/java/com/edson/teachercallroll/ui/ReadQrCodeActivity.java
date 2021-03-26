package com.edson.teachercallroll.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.edson.teachercallroll.R;
import com.edson.teachercallroll.viewmodel.ReadQrCodeViewModel;
import com.google.zxing.Result;

public class ReadQrCodeActivity extends AppCompatActivity {

    private int CAMERA_REQUEST_CODE = 100;
    private CodeScanner codeScanner;
    private CodeScannerView codeScannerView;
    private ReadQrCodeViewModel viewModel;
    SharedPreferences shPref;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_qr_code);
        if (ContextCompat.checkSelfPermission(ReadQrCodeActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= 23) {
                ActivityCompat.requestPermissions(ReadQrCodeActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
            }
        } else {
            setupComponents();
            initCodeScanner();
        }
    }


    public void setupComponents() {
        intent = getIntent();
        codeScannerView = findViewById(R.id.scanner_view);
        shPref = ReadQrCodeActivity.this.getSharedPreferences("TeacherCallRoll_ShPref", 0);
        viewModel = new ViewModelProvider(ReadQrCodeActivity.this).get(ReadQrCodeViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }

    public void initCodeScanner() {
        codeScanner = new CodeScanner(this, codeScannerView);
        codeScanner.setCamera(CodeScanner.CAMERA_BACK);
        codeScanner.setFormats(CodeScanner.ALL_FORMATS);
        codeScanner.setAutoFocusMode(AutoFocusMode.SAFE);
        codeScanner.setAutoFocusEnabled(true);
        codeScanner.setFlashEnabled(false);
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        long idSheet = intent.getLongExtra("idSheet", 0);
                        viewModel.addStudentAssistanceSheet(shPref.getString("auth_token", null), idSheet, Integer.parseInt(result.getText()))
                                .observe(ReadQrCodeActivity.this, (Observer<String>) reponse -> {
                                    Toast.makeText(ReadQrCodeActivity.this, reponse, Toast.LENGTH_LONG).show();
                                    finish();
                                });
                    }
                });
            }
        });
    }

}