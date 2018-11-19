package com.example.codoon.smartbutler.entity;

import cn.bmob.v3.BmobUser;

/**
 * 创建者：Sunday
 * 项目名：SmartButler
 * 包名：com.example.codoon.smartbutler.entity
 * 文件名：MyUser
 * 创建时间：2018/11/14 下午4:49
 * 描述：用户睡醒
 */
public class MyUser extends BmobUser {
    private int age;
    private boolean sex;
    private String desc;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "age=" + age +
                ", sex=" + sex +
                ", desc='" + desc + '\'' +
                '}';
    }

}
