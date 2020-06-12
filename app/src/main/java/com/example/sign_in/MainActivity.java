package com.example.sign_in;



import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class MainActivity extends AppCompatActivity {

    private ImageView mHBack;
    private ImageView mHHead;
    private ImageView mUserLine;
    private TextView mUserVal;

    private ItemView mUser_Name;
    private ItemView mGender;
    private ItemView mSignname;
    private ItemView mUpdate_user_passwd;
    private ItemView mUpdate_user_name;
    private ItemView mCancel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setData();
    }

    private void setData() {
        //设置背景磨砂效果
        Glide.with(this).load(R.drawable.head)
                .bitmapTransform(new BlurTransformation(this, 25), new CenterCrop(this))
                .into(mHBack);
        //设置圆形图像
        Glide.with(this).load(R.drawable.head)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(mHHead);

        //设置用户名整个item的点击事件
        mUser_Name.setItemClickListener(new ItemView.itemClickListener() {
            @Override
            public void itemClick(String text) {
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
        //修改用户名item的左侧图标
        mUser_Name.setLeftIcon(R.drawable.ic_update_user_name);

        mUser_Name.setLeftTitle("修改后的用户名");
        mUser_Name.setRightDesc("名字修改");
        mUser_Name.setShowRightArrow(false);
        mUser_Name.setShowBottomLine(false);

        //设置用户名整个item的点击事件
        mUser_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "我是onclick事件显示的", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initView() {
        //顶部头像控件
        mHBack = (ImageView) findViewById(R.id.h_back);
        mHHead = (ImageView) findViewById(R.id.h_head);
        mUserLine = (ImageView) findViewById(R.id.user_line);
        mUserVal = (TextView) findViewById(R.id.user_val);
        //下面item控件
        mUser_Name = (ItemView) findViewById(R.id.user_name);
        mGender = (ItemView) findViewById(R.id.gender);
        mSignname = (ItemView) findViewById(R.id.signName);
        mUpdate_user_passwd = (ItemView) findViewById(R.id.update_user_passwd);
        mUpdate_user_name = (ItemView) findViewById(R.id.update_user_name);
        mCancel = (ItemView) findViewById(R.id.cancel);
    }
}