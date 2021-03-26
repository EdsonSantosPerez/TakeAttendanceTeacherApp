package com.edson.teachercallroll.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.edson.teachercallroll.apidata.network.RetroClient;
import com.edson.teachercallroll.model.AssistanceSheetDto;
import com.edson.teachercallroll.model.StudentAssistance;
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

public class StudentsInSheetViewModel extends ViewModel {

    private int httpStatusCode;

    private MutableLiveData<String> studentJsonList = new MutableLiveData<>();
    private MutableLiveData<String> deleteReponse = new MutableLiveData<>();

    public MutableLiveData<String> showAssistanceSheetDetails(String token, long idSheet) {
        Call<ResponseBody> call = RetroClient.getInstance().getApi().showAssistanceSheetDetails(token, idSheet);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        studentJsonList.postValue(response.body().string());
                    } else {
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

    public MutableLiveData<String> deleteStudentFromSheet(String token, long id, int identifier) {
        Call<ResponseBody> call = RetroClient.getInstance().getApi().deleteStudentFromAssistanceSheet(token, id, identifier);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        deleteReponse.postValue("Bien supprimé");
                    } else {
                        deleteReponse.postValue("Un erreur s'est sourvenu");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                httpStatusCode = response.code();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                deleteReponse.postValue(t.getMessage());
            }
        });
        return deleteReponse;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

}
