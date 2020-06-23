package com.example.sign_in;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class secondActivity extends Activity {
    private EditText sign_code,state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        sign_code = (EditText)  findViewById(R.id.sign_code);
        state = (EditText)  findViewById(R.id.state);
    }
}