package com.example.sign_in;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }
        SharedPreferences config = getSharedPreferences("config", MODE_PRIVATE);
        String token = config.getString("token", "");
        if (token.isEmpty()){
            Log.d("StartActivate", "token is empty");
        }
        Log.d("StartActivate", token);
    }
}
