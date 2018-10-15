package com.example.fedsev.feedback;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


public class Setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    public void set(View view) {
        EditText ip,port;
        SharedPreferences sharedPreferences = this.getSharedPreferences("private_data", Context.MODE_PRIVATE);
        ip = findViewById(R.id.ip);
        port = findViewById(R.id.port);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("BASE_URL","http://" + ip.getText().toString() + ":" + port.getText().toString() + "/");
        editor.apply();
        Intent i = new Intent(Setting.this,Login.class);
        startActivity(i);
    }
}
