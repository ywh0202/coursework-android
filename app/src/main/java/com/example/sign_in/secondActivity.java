package com.example.sign_in;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class secondActivity extends Activity {
    private TextView  txt3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        txt3 = (TextView ) findViewById(R.id.txt3);
        Bundle bundle= this.getIntent() .getExtras();
        String str = bundle .getString("text");
        txt3.setText(str);




    }
}