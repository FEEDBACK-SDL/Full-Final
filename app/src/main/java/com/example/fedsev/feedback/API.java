package com.example.fedsev.feedback;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {
    static String BASE_URL = "http://192.168.43.154:3000/";
    @GET("profile")
    Call<DataFromServer> getData();
}
