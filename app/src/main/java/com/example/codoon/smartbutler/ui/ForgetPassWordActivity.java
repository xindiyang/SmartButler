package com.example.codoon.smartbutler.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.codoon.smartbutler.R;
import com.example.codoon.smartbutler.entity.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 创建者：Sunday
 * 项目名：SmartButler
 * 包名：com.example.codoon.smartbutler.ui
 * 文件名：ForgetPassWordActivity
 * 创建时间：2018/11/15 下午3:13
 * 描述：TODO
 */
public class ForgetPassWordActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText pass, passW, passWo, email;
    private Button change_btn, forget_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fogetorchange_activity);
        initView();
    }

    private void initView() {
        pass = findViewById(R.id.pass);
        passW = findViewById(R.id.passWo);
        passWo = findViewById(R.id.passWo);
        email = findViewById(R.id.userEmail);
        change_btn = findViewById(R.id.changePass);
        forget_btn = findViewById(R.id.forgetPass);
        change_btn.setOnClickListener(this);
        forget_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changePass:
                changePass();
                break;
            case R.id.forgetPass:
                forgetPass();
                break;
        }
    }

    /**
     * 修改密码逻辑
     */
    private void changePass() {
        //1.获取输入框的值
        String now = pass.getText().toString().trim();
        String new_pass = passW.getText().toString().trim();
        String new_passW = passWo.getText().toString().trim();
        //2.判断是否为空
        if (!TextUtils.isEmpty(now)&&!TextUtils.isEmpty(new_pass)&&!TextUtils.isEmpty(new_passW)){
            //3.判断两次新密码是否一致
            if (new_pass.equals(new_passW)){
                //4.重置密码
                MyUser.updateCurrentUserPassword(now, new_pass, new UpdateListener() {

                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                           Toast.makeText(ForgetPassWordActivity.this,
                                   "密码修改成功，可以用新密码进行登录啦", Toast.LENGTH_SHORT).show();
                           finish();
                        }else{
                            Toast.makeText(ForgetPassWordActivity.this,
                                    "失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }else {
                Toast.makeText(this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();

            }
        }else {
            Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * 忘记密码逻辑
     */
    private void forgetPass() {
        //1,获取输入框的邮箱
        final String emails = email.getText().toString().trim();
        //2,判断是否为空
        if (!TextUtils.isEmpty(emails)) {
            //3.发送邮件
            MyUser.resetPasswordByEmail(emails, new UpdateListener() {

                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Toast.makeText(ForgetPassWordActivity.this, "重置密码请求成功，请到"
                                + email + "邮箱进行密码重置操作", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ForgetPassWordActivity.this, "失败:"
                                + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
        }
    }
}
