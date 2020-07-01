package com.example.sign_in;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChakanActivity extends Activity {
    //发送http请求

    private Button btn;


    TextView name, limit, signid;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_item);
        name = (TextView) this.findViewById(R.id.sign_name);
        limit = (TextView) this.findViewById(R.id.time_limit);
        signid = (TextView) this.findViewById(R.id.sign_id);
        btn = (Button) this.findViewById(R.id.mButton);
        //获取前面页面传送的sign_id
       /* Bundle bunde=this.getIntent().getExtras();
        sign_id=bunde.getString("text");*/

        SharedPreferences config = getSharedPreferences("config", MODE_PRIVATE);
        String token = config.getString("token", "");
        Log.d("loginfor's token", token);
        if (!token.isEmpty()) {
            String result1 = HttpRequest.sendGet("http://218.78.85.248:8888/v1/sign/query_all_sign_in", "", token);//获取截至时间和用户姓名
            GSONAnalysis(result1);
        }
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setClass(ChakanActivity.this,SigninfoActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();
                String id=signid.getText().toString();
                Log.d("sign_id",id);
                bundle.putString("text", id);
                intent1.putExtras(bundle);
                startActivity(intent1);
            }

        });
    }


    public void GSONAnalysis(String results) {
        Gson gson = new Gson();
        //解析http请求返回的gson
        Cha cha = gson.fromJson(results, Cha.class);
        String state = cha.getState();
        Log.d("state", state);
        Log.d("payload", cha.getPayload().getCount());
        if (state == "true") {
            List<Sign_list1> lists = cha.getPayload().getSign_list();
            for (Sign_list1 list : lists) {
                String signname = list.getsign_name();
                name.setText("签到名称：    " + signname);
                String timelimit = list.gettime_limit();
                limit.setText("销毁时间：  " + timelimit);
                String sign_id = list.getsign_id();
                signid.setText(sign_id);
            }
        }

    }
}
class Cha {
        private  String state;
        private  Payloadss payload;
        public String getState() {
            return state;
        }
        public Payloadss getPayload() {
            return payload;
        }

    }
    class Payloadss {
        private String count;
        private List<Sign_list1> sign_list;
        public List<Sign_list1> getSign_list() {
            return sign_list;
        }

        public String getCount() {
            return count;

        }
    }
    class Sign_list1 {
        private String sign_name;
        private String time_limit;
        private String sign_id;
        public String getsign_name() {
            return sign_name;
        }

        public String gettime_limit() {
            return time_limit;
        }

        public String getsign_id() {
            return sign_id;
        }
    }

