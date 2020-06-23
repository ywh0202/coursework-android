package com.example.sign_in.uitls;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class okhttp {
    /*public static void post(String url, FormBody.Builder builder, Callback callback){
        OkHttpClient client = new OkHttpClient();
        FormBody build = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(build)
                .build();
        client.newCall(request).enqueue(callback);



    }*/
    public static void post(String sign_name, String sign_message,String time_limit,String token, Callback callback){
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("sign_name",sign_name)
                .add("sign_message",sign_message)
                .add("time_limit",time_limit )
                .build();
        Request request = new Request.Builder()
                .url("http://218.78.85.248:8888/v1/sign/init_sign_in")
                .addHeader("token","847f1f8f0bde740400212d783942a753")
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);

    }
}
