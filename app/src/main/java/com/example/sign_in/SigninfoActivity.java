package com.example.sign_in;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;


import java.util.List;

/**代码中TextView参数的说明：   sumnum:签到总人数  desc:签到描述   key:签到码     endtime:截止时间
                              statue:签到码状态  person:签到人员姓名
 */



public class SigninfoActivity extends Activity {
    TextView sumnum, desc, key, endtime, statue, person;
    private String originAddress = "http://218.78.85.248:8888/v1/sign/query_sign_in";
    private String sign_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TAG", " ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logininfor);
        sumnum = (TextView) this.findViewById(R.id.sumnum);
        desc = (TextView) this.findViewById(R.id.desc);
        key = (TextView) this.findViewById(R.id.key);
        endtime = (TextView) this.findViewById(R.id.endtime);
        statue = (TextView) this.findViewById(R.id.statue);
        person = (TextView) this.findViewById(R.id.person);
        //获取前面页面传送的dign_id
        Bundle bunde = this.getIntent().getExtras();
        sign_id = bunde.getString("text");
        Log.d("sign_id",sign_id);

        //发送http请求
        SharedPreferences config = getSharedPreferences("config", MODE_PRIVATE);
        String token = config.getString("token", "");
        Log.d("token'infor", token);
        if (!token.isEmpty()) {
            String param = "sign_id=" + sign_id;
            String result = HttpRequest.sendGet(originAddress, param, token);
            Log.d("result",result);
            Gson gson = new Gson();
            //解析http请求返回的gson
            logn_infor lognInfor = gson.fromJson(result, logn_infor.class);
            String state = lognInfor.getState();
            Log.d("inforstate", state);

            if (state == "true") {
                String count1 = lognInfor.getPayloads1().getSign_count();
                sumnum.setText(count1);
                String message = lognInfor.getPayloads1().getSign_message();
                desc.setText(message);
                String code = lognInfor.getPayloads1().getSign_code();
                key.setText(code);
                String limit = lognInfor.getPayloads1().getTime_limit();
                endtime.setText(limit);
                if(!lognInfor.getPayloads1().getSign_person().isEmpty()) {
                    List<String> person_name_list = lognInfor.getPayloads1().getSign_person();
                    for (String person_name : person_name_list) {
                        person.setText(person_name);
                    }
                }
                statue.setText("正在进行！");
            } else {
                Toast toast;
                toast = Toast.makeText(SigninfoActivity.this, "查询失败，请返回！", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
            }
        }

    }
}

//获取gsonz中state和payload
class logn_infor{
    private String state;
    private Payloads1 payload;
    public String getState(){
        return state;
    }
    public Payloads1 getPayloads1(){
        return payload;
    }
}
class Payloads1{
    String sign_code;
    String sign_count;
    String sign_message;
    List<String> sign_person;
    String time_limit;
    public String getSign_count(){
        return sign_count;
    }
    public String getSign_message(){
        return sign_message;
    }
    public List<String> getSign_person(){
        return sign_person;
    }
    public String getSign_code(){
        return sign_code;
    }
    public String getTime_limit(){
        return time_limit;
    }

}




