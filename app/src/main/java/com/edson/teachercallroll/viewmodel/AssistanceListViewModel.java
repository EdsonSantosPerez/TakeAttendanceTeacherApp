package com.edson.teachercallroll.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.edson.teachercallroll.apidata.network.RetroClient;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssistanceListViewModel extends ViewModel {

    private int httpStatusCode;
    public MutableLiveData<String> assistanceList = new MutableLiveData<>();

    public MutableLiveData<String> getAssistanceList(String token, int identifierNumber) {
        Call<ResponseBody> call = RetroClient.getInstance().getApi().getMyAssistanceList(token, identifierNumber);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        assistanceList.postValue(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    assistanceList.postValue(null);
                }
                httpStatusCode = response.code();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return assistanceList;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

}
