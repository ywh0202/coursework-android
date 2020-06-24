package com.example.sign_in;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class UserActivity extends AppCompatActivity {

    private ImageView mHBack;
    private ImageView mHHead;
    private ImageView mUserLine;
    private Button mGoBack;

    private ItemView mUser_name;
    private ItemView mGender;
    private ItemView mSignname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

                 Button btn1=findViewById(R.id.update_user_passwd);
                 btn1.setOnClickListener(view -> {
                   Intent it =new Intent();
                   it.setClass(UserActivity.this, Resetpwd.class);
                   UserActivity.this.startActivity(it);
        });
                Button btn2=findViewById(R.id.update_user_name);
                btn2.setOnClickListener(view -> {
                   Intent it =new Intent();
                   it.setClass(UserActivity.this, Resetname.class);
                   UserActivity.this.startActivity(it);
        });
            }


    private void initView() {
        //顶部头像控件
        mHBack = (ImageView) findViewById(R.id.h_back);
        mHHead = (ImageView) findViewById(R.id.h_head);
        mUserLine = (ImageView) findViewById(R.id.user_line);
        //下面item控件
        mUser_name = (ItemView) findViewById(R.id.user_name);
        mGender = (ItemView) findViewById(R.id.gender);
        mSignname = (ItemView) findViewById(R.id.signName);
    }


    public static void sendHttp(final HttpCallbackListener listener) {
        new Thread(() -> {
            URL url;
            HttpURLConnection connection = null;

            try {
                url = new URL("http://218.78.85.248:8888");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(8000);
                connection.setReadTimeout(8000);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                // 得到服务器返回数据
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
                if (listener != null) {
                    listener.onFinish(response.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (listener != null) {
                    listener.onError(e);
                }
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        });

    }

    public interface HttpCallbackListener {
        void onFinish(String response);

        void onError(Exception e);
    }
}
