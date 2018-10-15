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
    //static String BASE_URL = "http://192.168.0.105:8000/";
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

    @FormUrlEncoded
    @POST("submitData")
    Call<String> sendData(@Field("token") String token,
                    @Field("a1") String a1,
                    @Field("a2") String a2,
                    @Field("a3") String a3,
                    @Field("a4") String a4,
                    @Field("a5") String a5,
                    @Field("a6") String a6,
                    @Field("cust_id") String cust_id,
                    @Field("service_id") String service_id,
                    @Field("date") String date,
                    @Field("time") String time,
                    @Field("cust_rat") String cust_rat);
}
