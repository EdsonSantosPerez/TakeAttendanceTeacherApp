package com.edson.teachercallroll.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.edson.teachercallroll.apidata.network.RetroClient;
import com.edson.teachercallroll.apidata.request.UpdateStudentStatusRequest;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentsInSheetModifyViewModel extends ViewModel {

    private int httpStatusCode;
    public MutableLiveData<String> assistanceList = new MutableLiveData<>();
    private MutableLiveData<String> deleteReponse;

    public StudentsInSheetModifyViewModel() {
    }

    public MutableLiveData<String> showAssistanceSheetDetails(String token, long idSheet) {
        Call<ResponseBody> call = RetroClient.getInstance().getApi().showAssistanceSheetDetails(token, idSheet);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        assistanceList.postValue(response.body().string());
                    } else {
                        assistanceList.postValue(null);
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
        return assistanceList;
    }

    public void modifyStatus(String token, String assistanceSheetId, String identifierNumber, String status){
        Call<ResponseBody> call = RetroClient.getInstance().getApi().updateStudentStatus(token, new UpdateStudentStatusRequest(assistanceSheetId, identifierNumber, status));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){

                }else {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
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
    
    public MutableLiveData<String> modifuyAssistanceSheetDetails(String token, long idSheet) {
        Call<ResponseBody> call = RetroClient.getInstance().getApi().showAssistanceSheetDetails(token, idSheet);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        assistanceList.postValue(response.body().string());
                    } else {
                        assistanceList.postValue(null);
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
        return assistanceList;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

}
