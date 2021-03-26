package com.edson.teachercallroll.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edson.teachercallroll.R;
import com.edson.teachercallroll.viewmodel.GenerateQrCodeViewModel;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class GenerateQrCodeActivity extends AppCompatActivity {

    private ImageView imgVQRCode;
    private TextView txtVCreateSheetDate;
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;
    private GenerateQrCodeViewModel viewModel;
    private String idSheet = "";

    private SharedPreferences shPref;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr_code);
        setUpComponents();
        createQR();
    }

    public void setUpComponents() {
        shPref = GenerateQrCodeActivity.this.getSharedPreferences("TeacherCallRoll_ShPref", 0);
        viewModel = new ViewModelProvider(GenerateQrCodeActivity.this).get(GenerateQrCodeViewModel.class);
        imgVQRCode = findViewById(R.id.imgVQRCode);
        txtVCreateSheetDate = findViewById(R.id.txtVCreateSheetDate);
    }

    public void createQR() {
        try {
            Intent intent = getIntent();
            idSheet = intent.getStringExtra("idSheet");
//            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(intent.getStringExtra("feuilleDate"));
//            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            txtVCreateSheetDate.setText(intent.getStringExtra("dateSheet"));
            qrgEncoder = new QRGEncoder(idSheet, null, QRGContents.Type.TEXT, 500);
            qrgEncoder.setColorBlack(Color.parseColor("#264400"));
            bitmap = qrgEncoder.getBitmap();
            imgVQRCode.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkAssistanceSheet(View view) {
        SharedPreferences shPref = getApplicationContext().getSharedPreferences("TeacherCallRoll_ShPref", 0);
        String token = shPref.getString("auth_token", null);
        viewModel.getStudentList(token, Long.parseLong(idSheet)).observe(GenerateQrCodeActivity.this, (Observer<String>) jsonList -> {
            if (viewModel.getHttpStatusCode() == 401 || viewModel.getHttpStatusCode() == 403) {
                singOut();
            } else {
                if (jsonList != null) {
                    startStudentsInsheetActivity();
                }
            }
        });
    }

    public void startStudentsInsheetActivity(){
        intent = new Intent(GenerateQrCodeActivity.this, StudentsInSheetActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("idSheet", Long.parseLong(idSheet));
        startActivity(intent);
    }

    public void singOut() {
        Toast.makeText(GenerateQrCodeActivity.this, "Vous n'avez plus d’accès sur l'appli\nVeuillez vous reconnecter", Toast.LENGTH_LONG).show();
        SharedPreferences.Editor editor = shPref.edit();
        editor.clear();//delete all data from shared preferences
        editor.commit();//commit the changes
        intent = new Intent(GenerateQrCodeActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}