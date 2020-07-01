package com.example.sign_in;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.google.gson.Gson;

import java.util.zip.Deflater;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class UserActivity extends AppCompatActivity {

    public static Deflater instance;
    private ImageView mHBack;
    private ImageView mHHead;
    private ImageView mUserLine;

    private TextView Username;
    private TextView Gender;
    private TextView Useraccount;

    String user_account;
    String gender;
    String user_name;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Username = (TextView) findViewById(R.id.user_name);
        Gender = (TextView) findViewById(R.id.gender);
        Useraccount = (TextView) findViewById(R.id.user_account);

        SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        //Log.d("YY", token);

        if (!token.isEmpty()){
            String return_json = HttpRequest.sendGet("http://218.78.85.248:8888/v1/user/query_message", "", token);//
            GSONAnalysis(return_json);
        }

        Button btn1 = findViewById(R.id.update_user_passwd);
        Button btn2 = findViewById(R.id.update_user_name);
        Button btn3 = findViewById(R.id.cancel);



        initView();
        setData();
    }

    private void setData() {
        //设置背景磨砂效果
        Glide.with(this).load(R.drawable.back4)
                .bitmapTransform(new BlurTransformation(this, 25), new CenterCrop(this))
                .into(mHBack);
        //设置圆形图像
        Glide.with(this).load(R.drawable.head1)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(mHHead);
        Button btn1 = findViewById(R.id.update_user_passwd);
        btn1.setOnClickListener(view -> {
            Intent it = new Intent();
            it.setClass(UserActivity.this, Resetpwd.class);
            UserActivity.this.startActivity(it);
        });
        Button btn2 = findViewById(R.id.update_user_name);
        btn2.setOnClickListener(view -> {
            Intent it = new Intent();
            it.setClass(UserActivity.this, Resetname.class);
            UserActivity.this.startActivity(it);
        });
        Button btn3 = findViewById(R.id.cancel);
        btn3.setOnClickListener(view -> {
            token="";
            SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("token",token);
            Intent it =new Intent();
            it.setClass(UserActivity.this,LoginActivity.class);
            UserActivity.this.startActivity(it);
        });
    }


    private void initView() {
        //顶部头像控件
        mHBack = (ImageView) findViewById(R.id.h_back);
        mHHead = (ImageView) findViewById(R.id.h_head);
        mUserLine = (ImageView) findViewById(R.id.user_line);
        //下面item控件
    }

    public void GSONAnalysis(String return_json) {

        Gson gson = new Gson();
        UserData userData = gson.fromJson(return_json, UserData.class);

        String state = userData.getState();

        //Log.d("UU", state);

        Log.d("gson_info",return_json);
        String user_name = userData.getPayload().getUser_name();
        Username.setText(user_name);
        String gender = userData.getPayload().getGender();
        Gender.setText(gender);
        String user_account = userData.getPayload().getUser_account();
        Useraccount.setText(user_account);

        //Log.d("user_name", user_name);
        // Log.d("gender", gender);
        //Log.d("user_count", user_account);

    }
}


