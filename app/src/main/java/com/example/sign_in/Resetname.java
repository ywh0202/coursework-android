package com.example.sign_in;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Resetname extends AppCompatActivity {
    private EditText mName_old;                        //密码编辑
    private EditText mName_new;                        //密码编辑
    private EditText mNameCheck;                       //密码编辑
    private Button resetname_btn_sure;
    private String Name_old,Name_new,name_check;
    private String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resetname);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    private void init() {

        mName_old = (EditText) findViewById(R.id.resetname_edit_name_old);
        mName_new = (EditText) findViewById(R.id.resetname_edit_name_new);
        mNameCheck = (EditText) findViewById(R.id.resetname_edit_name_check);
        resetname_btn_sure = (Button) findViewById(R.id.resetname_btn_sure);
        Button resetname_btn_cancel = (Button) findViewById(R.id.resetname_btn_cancel);
        resetname_btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resetname.this.finish();
            }
        });

        resetname_btn_sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getEditString();
                if (TextUtils.isEmpty(Name_old)) {
                    Toast.makeText(Resetname.this, "请输入原始昵称", Toast.LENGTH_SHORT).show();
                    return;
                } else if (equals(readPsw())) {
                    Toast.makeText(Resetname.this, "输入的昵称与原始昵称不一致", Toast.LENGTH_SHORT).show();
                    return;
                } else if (equals(readPsw())) {
                    Toast.makeText(Resetname.this, "输入的新昵称与原始昵称不能一致", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(Name_new)) {
                    Toast.makeText(Resetname.this, "请输入新昵称", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(Name_new)) {
                    Toast.makeText(Resetname.this, "请再次输入昵称", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!Name_new.equals(mNameCheck)) {
                    Toast.makeText(Resetname.this, "两次输入的新昵称不一致", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(Resetname.this, "新昵称设置成功", Toast.LENGTH_SHORT).show();
                    //修改登录成功时保存在SharedPreferences中的昵称
                    resetname(Name_new);
                    Intent intent = new Intent(Resetname.this, LoginActivity.class);
                    startActivity(intent);
                    UserActivity.instance.finish();//关闭设置页
                    Resetname.this.finish();//关闭当前页面
                }
            }
        });
    }

    private void getEditString(){

        String passwd_old=mName_old.getText().toString().trim();
        String passwd_new=mName_new.getText().toString().trim();
        String passwdCheck=mNameCheck.getText().toString().trim();
    }
    /**
     * 修改登录成功时保存在SharedPreferences中的密码
     */
    private void resetname(String Name_new) {
        SharedPreferences sp = getSharedPreferences("login", 0);
        SharedPreferences.Editor editor = sp.edit();//获取编辑器
        editor.putString("user_name","");//保存新昵称
        editor.commit();//提交修改
    }

    /**
     * 从SharedPreferences中读取原始密码
     * 密码和用户名作为键值对保存到一起，所以通过用户名读取密码
     * 用户名从AnalysisUtils工具类获取
     */
    private String readPsw() {
        SharedPreferences sp = getSharedPreferences("login", 0);
        String user_name = sp.getString("user_name", "");
        return user_name;
    }
}

