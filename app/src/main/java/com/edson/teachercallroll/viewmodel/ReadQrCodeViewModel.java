package com.edson.teachercallroll.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.edson.teachercallroll.apidata.network.RetroClient;
import com.google.gson.JsonParser;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadQrCodeViewModel extends ViewModel {

    private int httpStatusCode;
    MutableLiveData<String> reponse;

    public MutableLiveData<String> addStudentAssistanceSheet(String token, long idSheet, int identifierNumber) {
        Call<ResponseBody> call = RetroClient.getInstance().getApi().addStudentAssistanceSheet(token, idSheet, identifierNumber);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JsonParser parser = new JsonParser();
                    if (response.isSuccessful()) {
                        reponse.postValue(""+ response.code());
                    } else {
                        reponse.postValue(response.errorBody().string());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                httpStatusCode = response.code();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                reponse.postValue("Failure: " + t.getMessage());
            }
        });
        return reponse;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

}
