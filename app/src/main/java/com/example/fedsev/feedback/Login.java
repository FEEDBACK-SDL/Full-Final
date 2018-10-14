package com.example.fedsev.feedback;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.security.AccessController.getContext;


public class Login extends AppCompatActivity {
    EditText username,password;
    View avi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        checkAuth();
        password = findViewById(R.id.password);
    }

    public void goNext(View view) {
        syncData();
        Intent i = new Intent(Login.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    public void checkAuth(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("private_data", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");
        if(!token.equals("")){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            avi = findViewById(R.id.avi);
            avi.setVisibility(View.VISIBLE);
            API api = retrofit.create(API.class);
            Call<String> call = api.getAuth(token);
           call.enqueue(new Callback<String>() {
               @Override
               public void onResponse(Call<String> call, Response<String> response) {
                   Log.d("asd",response.body());
                   if(response.body().equals("true")){
                       syncData();
                       Intent i = new Intent(Login.this,MainActivity.class);
                       startActivity(i);
                       finish();
                   }
               }

               @Override
               public void onFailure(Call<String> call, Throwable t) {
                   Log.d("asd","error");
                   avi.setVisibility(View.GONE);
               }
           });
            avi.setVisibility(View.GONE);
        }
    }

    public void loginfunc(View view) {
        String uname = username.getText().toString();
        String pass = password.getText().toString();

            if(!uname.equals("") && !pass.equals("")){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            avi = findViewById(R.id.avi);
            avi.setVisibility(View.VISIBLE);
            API api = retrofit.create(API.class);
            Call<LoginData> call = api.loginD(uname,pass);
            call.enqueue(new Callback<LoginData>() {
                @Override
                public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                    LoginData ld = response.body();
                    if(ld.getToken().equals("-1")){
                        avi.setVisibility(View.GONE);
                        FancyToast.makeText(Login.this,"Password Wrong",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    }
                    else if(ld.getToken().equals("-2")){
                        avi.setVisibility(View.GONE);
                        FancyToast.makeText(Login.this,"Username Wrong",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    }
                    else {
                        save(ld);
                        FancyToast.makeText(Login.this,ld.getToken(),FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                        avi.setVisibility(View.GONE);
                        goNext(view);
                        //FancyToast.makeText(Login.this,ld.getToken(),FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginData> call, Throwable t) {

                }
            });
        }
        else {
                FancyToast.makeText(Login.this,"Enter Details",FancyToast.LENGTH_LONG,FancyToast.WARNING,false).show();
            }
    }
    public void save(LoginData ld){
        SharedPreferences sharedPreferences = getSharedPreferences("private_data",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token",ld.getToken());
        editor.putString("first_name",ld.getFirst_name());
        editor.putString("last_name",ld.getLast_name());
        editor.putString("contact",ld.getContact());
        editor.putString("email",ld.getEmail());
        editor.putInt("rating",ld.getRating());
        editor.apply();
    }
public void syncData() {
    SharedPreferences sharedPreferences = this.getSharedPreferences("private_data", Context.MODE_PRIVATE);
    String token = sharedPreferences.getString("token", "");
    String ldate = sharedPreferences.getString("date","2018-10-13");
    DateTimeFormatter dff = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    LocalDateTime now = LocalDateTime.now();
    String nowdate = dff.format(now);
    Date date1 = null,date2 = null;
    try {
        date1 = sdf.parse(ldate);
        date2 = sdf.parse(nowdate);
    } catch (ParseException e) {
        e.printStackTrace();
    }
    Log.d(nowdate,ldate);
    if (!token.equals("") && date2.after(date1) ) {
        Log.d(nowdate,ldate);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("date",nowdate);
        editor.apply();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        avi = findViewById(R.id.avi);
        avi.setVisibility(View.VISIBLE);
        API api = retrofit.create(API.class);
        Call<ArrayList<SyncData>> call = api.sync(token);
        call.enqueue(new Callback<ArrayList<SyncData>>() {
            @Override
            public void onResponse(Call<ArrayList<SyncData>> call, Response<ArrayList<SyncData>> response) {
                ArrayList<SyncData> arrayList = response.body();
                for (SyncData syncData : arrayList) {
                    Log.d("asd", syncData.getModel());

                    try {
                        syncData.setCallisdone(0);
                        MainActivity.myAppDatabase.myDao().addSyncData(syncData);
                    }
                    catch (Exception e){

                    }
                }
                avi.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<SyncData>> call, Throwable t) {
                avi.setVisibility(View.GONE);
            }
        });
    }
}
}
