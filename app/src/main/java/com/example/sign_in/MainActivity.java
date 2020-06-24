package com.example.sign_in;

import android.app.DownloadManager;
import android.graphics.Color;
import android.media.session.MediaSession;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import okhttp3.FormBody;
import okhttp3.Request;

import com.example.sign_in.R;
import com.example.sign_in.secondActivity;
import com.example.sign_in.R;

public class MainActivity extends  Activity
{
    private Button btn1;
    private EditText edit1,edit2,edit3;
    private TextView txt1,txt2,sign_name,sign_message,time_limit;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState );
        setContentView(R.layout.activity_main );
        txt1= (TextView) findViewById(R.id.txt1);
        txt2= (TextView) findViewById(R.id.txt2);
        edit1 = (EditText)  findViewById(R.id.edit1);
        edit2 = (EditText)  findViewById(R.id.edit2);
        edit3 = (EditText)  findViewById(R.id.edit3);
        sign_name = (TextView) findViewById(R.id.sign_name);
        sign_message = (TextView) findViewById(R.id.sign_message);
        time_limit  = (TextView) findViewById(R.id.time_limit);
        btn1 = (Button) findViewById(R.id.button1);
        btn1.setOnClickListener(new mClick());
    }
    class mClick  implements OnClickListener
    {
        public void onClick(View v) {
            send();
        }
    };
    private void send() {
        //开启线程，发送请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    //url
                    URL url = new URL("http://218.78.85.248:8888/v1/sign/init_sign_in");
                    connection = (HttpURLConnection) url.openConnection();
                    //设置请求方法
                    connection.setRequestMethod("POST");
                    //设置连接超时时间（毫秒）
                    connection.setConnectTimeout(5000);
                    //设置读取超时时间（毫秒）
                    connection.setReadTimeout(5000);

                    //返回输入流
                    InputStream in = connection.getInputStream();
                    //读取输入流
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    show(result.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {//关闭连接
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
    private void show(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txt2.setText(result);
                Intent  intent = new Intent();
                intent.setClass(MainActivity .this, secondActivity.class) ;
                Bundle bundle = new Bundle();
                bundle.putString("text", txt2.getText().toString()) ;
                intent.putExtras(bundle);
                finish();
                startActivity(intent) ;

            }
        });
    }
}