package com.edson.teachercallroll.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.edson.teachercallroll.apidata.network.RetroClient;
import com.edson.teachercallroll.apidata.request.UpdateInfoRequest;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsViewModel extends ViewModel {

    private int httpStatusCode;

    private String email;
    private String identifierNumber;
    private String lastName;
    private String firstName;
    private String password;
    private MutableLiveData<Integer> code = new MutableLiveData<>();
    private MutableLiveData<String> myInfo = new MutableLiveData<>();

    public MutableLiveData<String> getMyInformation(String token, int idNumber) {
        Call<ResponseBody> call = RetroClient.getInstance().getApi().getMyInformation(token, idNumber);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JsonParser parser = new JsonParser();
                    if (response.isSuccessful()) {
                        JsonObject jso = (JsonObject) parser.parseString(response.body().string());
                        myInfo.postValue(response.body().string());
                        email = jso.get("email").getAsString();
                        identifierNumber = jso.get("identifierNumber").getAsString();
                        lastName = jso.get("lastName").getAsString();
                        firstName = jso.get("name").getAsString();
//                        password = jso.get("password").getAsString();
                    } else {
                        myInfo.postValue(null);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return myInfo;
    }

    public MutableLiveData<Integer> modifyPassword(String token, String identifierNumber, String password) {
        Call<ResponseBody> call = RetroClient.getInstance().getApi().
                updateMyInformation(token, new UpdateInfoRequest(identifierNumber, password));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                httpStatusCode = response.code();
                code.postValue(response.code());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return code;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getIdentifierNumber() {
        return identifierNumber;
    }
}
