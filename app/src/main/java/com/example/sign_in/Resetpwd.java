package com.example.sign_in;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Resetpwd extends AppCompatActivity {
    private EditText mUser_name;                        //用户名编辑
    private EditText mPasswd_old;                        //密码编辑
    private EditText mPasswd_new;                        //密码编辑
    private EditText mPasswdCheck;                       //密码编辑
    private Button mSureButton;                       //确定按钮
    private Button mCancelButton;                     //取消按钮
    private UserDataManager mUserDataManager;         //用户数据管理类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resetpwd);
        mUser_name = (EditText) findViewById(R.id.resetpwd_edit_name);
        mPasswd_old = (EditText) findViewById(R.id.resetpwd_edit_pwd_old);
        mPasswd_new = (EditText) findViewById(R.id.resetpwd_edit_pwd_new);
        mPasswdCheck = (EditText) findViewById(R.id.resetpwd_edit_pwd_check);

        mSureButton = (Button) findViewById(R.id.resetpwd_btn_sure);
        mCancelButton = (Button) findViewById(R.id.resetpwd_btn_cancel);

        mSureButton.setOnClickListener(m_resetpwd_Listener);      //注册界面两个按钮的监听事件
        mCancelButton.setOnClickListener(m_resetpwd_Listener);
        if (mUserDataManager == null) {
            mUserDataManager = new UserDataManager(this);
            mUserDataManager.openDataBase();                              //建立本地数据库
        }
    }
    View.OnClickListener m_resetpwd_Listener = new View.OnClickListener() {    //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.resetpwd_btn_sure:                       //确认按钮的监听事件
                    resetpwd_check();
                    break;

            }
        }
    };
    public void resetpwd_check() {                                //确认按钮的监听事件
        if (isUserNameAndPwdValid()) {
            String user_name = mUser_name.getText().toString().trim();
            String passwd_old = mPasswd_old.getText().toString().trim();
            String passwd_new = mPasswd_new.getText().toString().trim();
            String userPasswdCheck = mPasswdCheck.getText().toString().trim();
            int result=mUserDataManager.findUserByNameAndPasswd(user_name, passwd_old);
            if(result==1){                                             //返回1说明用户名和密码均正确,继续后续操作
                if(passwd_new.equals(userPasswdCheck)==false){           //两次密码输入不一样
                    Toast.makeText(this, getString(R.string.pwd_not_the_same),Toast.LENGTH_SHORT).show();
                    return ;
                } else {

                    Object mUser = null;
                    boolean flag = mUserDataManager.updateUserData(mUser);
                    if (flag == false) {
                        Toast.makeText(this, getString(R.string.resetpwd_fail),Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, getString(R.string.resetpwd_success),Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }else if(result==0){                                       //返回0说明用户名和密码不匹配，重新输入
                Toast.makeText(this, getString(R.string.pwd_not_fit_user),Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }
    @SuppressLint("StringFormatInvalid")
    public boolean isUserNameAndPwdValid() {
        String user_name = mUser_name.getText().toString().trim();
        //检查用户是否存在
        int count=mUserDataManager.findUserByName(user_name);
        //用户不存在时返回，给出提示文字
        if(count<=0){
            Toast.makeText(this, getString(R.string.name_not_exist, user_name),Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mUser_name.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.account_empty),Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPasswd_old.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_empty),Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPasswd_new.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_new_empty),Toast.LENGTH_SHORT).show();
            return false;
        }else if(mPasswdCheck.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_check_empty),Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
