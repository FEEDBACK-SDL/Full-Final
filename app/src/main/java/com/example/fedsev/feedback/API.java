package com.example.fedsev.feedback;

import android.content.Intent;

import org.androidannotations.annotations.rest.Post;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API {
    static String BASE_URL = "http://192.168.0.114:8000/";
    @GET("profile")
    Call<DataFromServer> getData();

    @FormUrlEncoded
    @POST("verify")
    Call<String> getAuth(@Field("token") String token);

    @FormUrlEncoded
    @POST("dayFetch")
    Call<ArrayList<SyncData>> sync(@Field("token") String token);

    @FormUrlEncoded
    @POST("login")
    Call<LoginData> loginD(@Field("uname")String uname,
                 @Field("pass")String pass);
}
