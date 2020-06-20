package com.example.sign_in;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ImageView ;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.graphics.Color;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

public class MainActivity extends  Activity
{
    private Button btn1;
    private EditText edit;
    private TextView txt1,txt2,txt3;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState );
        setContentView(R.layout.activity_main );
        txt1= (TextView) findViewById(R.id.testView1);
        txt2= (TextView) findViewById(R.id.testView2);
        txt3= (TextView) findViewById(R.id.testView3);
        btn1 = (Button) findViewById(R.id.button1);
        btn1.setOnClickListener(new mClick());
    }
    class mClick  implements OnClickListener
    {
        public void onClick(View v) {
            finish();
            Intent  intent = new Intent(MainActivity .this, secondActivity.class);
            startActivity(intent) ;

        }
    }
}