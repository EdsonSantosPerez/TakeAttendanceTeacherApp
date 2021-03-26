package com.edson.teachercallroll.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.edson.teachercallroll.apidata.network.RetroClient;
import com.edson.teachercallroll.model.FormationDto;
import com.edson.teachercallroll.model.StudentDto;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenerateQrCodeViewModel extends ViewModel {

    private int httpStatusCode;
    MutableLiveData<String> studentJsonList = new MutableLiveData<>();

    public MutableLiveData<String> getStudentList(String token, long idSheet) {
        Call<ResponseBody> call = RetroClient.getInstance().getApi().showAssistanceSheetDetails(token, idSheet);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JsonParser parser = new JsonParser();
                    if (response.isSuccessful()) {
                        studentJsonList.postValue(response.body().string());
                    } else {
                        JsonObject jso = (JsonObject) parser.parseString(response.errorBody().string());
                        studentJsonList.postValue(null);
                    }
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                httpStatusCode = response.code();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return studentJsonList;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

}
