package com.edson.teachercallroll.viewmodel;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.edson.teachercallroll.apidata.network.RetroClient;
import com.edson.teachercallroll.apidata.request.CreateSheetRequest;
import com.edson.teachercallroll.model.FormationDto;
import com.edson.teachercallroll.model.GroupDto;
import com.edson.teachercallroll.model.ModuleDto;
import com.edson.teachercallroll.model.SemestreDto;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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

public class SelectGroupViewModel extends ViewModel {

    private int httpStatusCode;

    private MutableLiveData<List<FormationDto>> formationList;
    private MutableLiveData<String> id = new MutableLiveData<>();
    private String date;

    public MutableLiveData<List<FormationDto>> getFormationList() {
        Call<ResponseBody> call = RetroClient.getInstance().getApi().getFormations();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        Type formationsListType = new TypeToken<ArrayList<FormationDto>>() {
                        }.getType();
                        Gson gson = new Gson();
                        formationList = gson.fromJson(response.body().string(), formationsListType);
                    } else {
                        formationList = null;
                    }
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                formationList = null;
            }
        });
        return formationList;
    }

    public MutableLiveData<String> addAsistanceSheet(String token, long idSemestre, long idModule, long idGroup) {
        Call<ResponseBody> call = RetroClient.getInstance().getApi().addAssistanceSheet(token, new CreateSheetRequest(idSemestre, idModule, idGroup));//70 7 1
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JsonParser parser = new JsonParser();
                    if (response.isSuccessful()) {
//                            Log.i("CREATE SHEET", "onResponse: body\n" + response.body().string());
                        JsonObject jso = (JsonObject) parser.parseString(response.body().string());
                        id.postValue(jso.get("assistanceSheetId").getAsString());
                        date = jso.get("startDate").getAsString();
                    } else {
                        JsonObject jso = (JsonObject) parser.parseString(response.errorBody().string());
                        id.postValue(null);
                    }
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                httpStatusCode = response.code();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                id.postValue(null);
            }
        });
        return id;
    }

    public String getDate() {
        return date;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}
