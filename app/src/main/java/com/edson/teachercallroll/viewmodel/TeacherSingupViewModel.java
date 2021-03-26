package com.edson.teachercallroll.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.edson.teachercallroll.apidata.network.RetroClient;
import com.edson.teachercallroll.apidata.request.SingupRequest;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherSingupViewModel extends ViewModel {

    private int httpStatusCode;
    private MutableLiveData<String> message = new MutableLiveData<>();

    public MutableLiveData<String> singupTeacher(String token, String email, String identifierNumber, String lastName, String firstName, String password) {
        Call<ResponseBody> call = RetroClient.getInstance().getApi().
                teacherSingup(token, new SingupRequest(email, identifierNumber, lastName, firstName, password));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JsonParser parser = new JsonParser();
                try {
                    if (response.isSuccessful()) {
                        JsonObject jso = (JsonObject) parser.parseString(response.body().string());
                        message.postValue(jso.get("message").getAsString());
                    } else {
                        JsonObject jso1 = (JsonObject) parser.parseString(response.errorBody().string());
                        message.postValue(jso1.get("message").toString());
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
        return message;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}
