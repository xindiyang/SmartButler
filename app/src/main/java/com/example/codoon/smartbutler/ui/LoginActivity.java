package com.example.codoon.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codoon.smartbutler.MainActivity;
import com.example.codoon.smartbutler.R;
import com.example.codoon.smartbutler.entity.MyUser;
import com.example.codoon.smartbutler.utils.ShareUtil;
import com.example.codoon.smartbutler.view.CustomDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 创建者：Sunday
 * 项目名：SmartButler
 * 包名：com.example.codoon.smartbutler.ui
 * 文件名：LoginActivity
 * 创建时间：2018/11/14 下午2:38
 * 描述：登录功能
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button register, login;
    private EditText userName, passWord;
    private CheckBox rememberPassword;
    private TextView chagngeOrforgetPassword;
    private CustomDialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    //初始化数据
    private void initView() {
        register = findViewById(R.id.register_btn);
        login = findViewById(R.id.login_btn);
        userName = findViewById(R.id.userName);
        passWord = findViewById(R.id.passWord);
        chagngeOrforgetPassword = findViewById(R.id.forgetOrchangePass);
        register.setOnClickListener(this);
        login.setOnClickListener(this);
        chagngeOrforgetPassword.setOnClickListener(this);
        rememberPassword = findViewById(R.id.rememberPass);
        //初始化dialog
        customDialog = new CustomDialog(this,100,100,
                R.layout.dialog_loding,R.style.Theme_dialog,Gravity.CENTER,R.style.pop_anim_style);
        //屏幕外点击无效
        customDialog.setCancelable(false);
        //设置选中的状态
        boolean isKeep = ShareUtil.getBoolean(this, "keeppass", true);
        rememberPassword.setChecked(isKeep);
        if (isKeep) {
            //设置密码
            userName.setText(ShareUtil.getString(this, "name", ""));
            passWord.setText(ShareUtil.getString(this, "password", ""));
        }
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_btn:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.login_btn:
                loginView();
                break;
            case R.id.forgetOrchangePass:
                startActivity(new Intent(this,ForgetPassWordActivity.class));

        }
    }
    /**
     *  登录逻辑
     */
    private void loginView() {
        //1.获取输入框的值
        String name = userName.getText().toString().trim();
        String password = passWord.getText().toString().trim();
        //2.判断是否为空
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
            customDialog.show();
            //登录
            final MyUser myUser = new MyUser();
            myUser.setUsername(name);
            myUser.setPassword(password);
            myUser.login(new SaveListener<Object>() {
                @Override
                public void done(Object o, BmobException e) {
                    customDialog.dismiss();
                    //判断结果
                    if (e == null) {
                        //判断邮箱是否验证
                        if (myUser.getEmailVerified()) {
                            //跳转
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "邮箱验证失败,请前往邮箱验证！", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 假如我现在输入用户名和密码，但我不点击登录，而是直接退出
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //保存状态
        ShareUtil.putBoolean(this, "keepPass", rememberPassword.isChecked());
        //是否记住密码
        if (rememberPassword.isChecked()) {
            //记住用户名和密码
            ShareUtil.putString(this, "name", userName.getText().toString().trim());
            ShareUtil.putString(this, "password", passWord.getText().toString().trim());
        } else {
            //清除用户名和密码
            ShareUtil.deleShare(this, "name");
            ShareUtil.deleShare(this, "password");

        }
    }
}
