package com.example.codoon.smartbutler.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.codoon.smartbutler.R;
import com.example.codoon.smartbutler.entity.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 创建者：Sunday
 * 项目名：SmartButler
 * 包名：com.example.codoon.smartbutler.ui
 * 文件名：RegisterActivity
 * 创建时间：2018/11/14 下午3:13
 * 描述：注册功能
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText userName, passWord, age, desc, againPass, email;
    private RadioGroup radioGroup;
    private Button registerBtn;
    private boolean isGender = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        userName = findViewById(R.id.username);
        passWord = findViewById(R.id.password);
        againPass = findViewById(R.id.againPass);
        age = findViewById(R.id.userOld);
        desc = findViewById(R.id.desc);
        email = findViewById(R.id.email);
        radioGroup = findViewById(R.id.radioGroup);
        registerBtn = findViewById(R.id.register_bt);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_bt:
                //获取到输入框的值
                String name = userName.getText().toString().trim();
                String ages = age.getText().toString().trim();
                String password = passWord.getText().toString().trim();
                String againpass = againPass.getText().toString().trim();
                String descs = desc.getText().toString().trim();
                String emails = email.getText().toString().trim();
                //判断是否为空
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password) &&
                        !TextUtils.isEmpty(againpass) && !TextUtils.isEmpty(ages) &&
                        !TextUtils.isEmpty(emails)) {
                    //判断两次输入的密码是否一致
                    if (password.equals(againpass)) {
                        //先把性别判断一下
                        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                if (checkedId == R.id.man) {
                                    isGender = true;
                                } else if (checkedId == R.id.women) {
                                    isGender = false;
                                }
                            }
                        });
                        //判断简介是否为空
                        if (TextUtils.isEmpty(descs)) {
                            descs = "这个人很懒，什么都没有留下";
                        }
                        //注册
                        MyUser myUser = new MyUser();
                        myUser.setUsername(name);
                        myUser.setPassword(password);
                        myUser.setEmail(emails);
                        myUser.setAge(Integer.parseInt(ages));
                        myUser.setDesc(descs);
                        myUser.setSex(isGender);
                        //注意：不能用save方法进行注册
                        myUser.signUp(new SaveListener<MyUser>() {
                            @Override
                            public void done(MyUser s, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "注册失败:" + e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    } else {
                        Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }
}
