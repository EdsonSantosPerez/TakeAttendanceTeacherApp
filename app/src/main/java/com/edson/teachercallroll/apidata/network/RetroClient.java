package com.edson.teachercallroll.apidata.network;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

    private static final String BASE_URL = "http://192.168.43.153:8080/";//http://192.168.43.202:8080/  |   http://localhost:8080/
    private static RetroClient RClientInstance;
    private Retrofit retrofit;

    public RetroClient() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetroClient getInstance() {
        if (RClientInstance == null) {
            RClientInstance = new RetroClient();
        }
        return RClientInstance;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }
}

//    @Override
//    protected Response<String> parseNetworkResponse(NetworkResponse response) {
//        String token = response.headers.get("token");
//        ...
//    }
