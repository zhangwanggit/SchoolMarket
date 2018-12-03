package com.mcc.sharedPreferences;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import com.mcc.data.User;

/**
 * Created by zw on 2018/4/28.
 */

public class MySharePreferences {
    private ContextWrapper mWrapper = null;
    private static MySharePreferences mySharePreferences = null;
    private SharedPreferences mPreferences = null;



    private Boolean isLogin=false;
    private User user=new User();

    // 单例模式获取实例
    public static MySharePreferences GetInstance(Context base) {
        if (mySharePreferences == null) {
            mySharePreferences = new MySharePreferences(base);
            mySharePreferences.user.setUserId(mySharePreferences.mPreferences
                    .getInt("userId", 0));
            mySharePreferences.user.setPhone(mySharePreferences.mPreferences
                    .getString("phone", ""));
            mySharePreferences.user.setPassword(mySharePreferences.mPreferences
                    .getString("password", ""));
            mySharePreferences.user.setSchool_id(mySharePreferences.mPreferences
                    .getInt("school_id", 0));
            mySharePreferences.user.setName(mySharePreferences.mPreferences
                    .getString("name", ""));
            mySharePreferences.user.setSex(mySharePreferences.mPreferences
                    .getBoolean("sex", false));
            mySharePreferences.user.setStudentNum(mySharePreferences.mPreferences
                    .getString("student_num", ""));
            mySharePreferences.user.setHead_image(mySharePreferences.mPreferences
                    .getString("head_img", ""));
            mySharePreferences.user.setNick_name(mySharePreferences.mPreferences
                    .getString("nick_name", ""));
            mySharePreferences.setLogin(mySharePreferences.mPreferences.getBoolean("login",false));
        }
        return mySharePreferences;
    }
    // 配置getSharedPreferences
    public MySharePreferences(Context base) {
        // super(base);
        mWrapper = new ContextWrapper(base);
        mPreferences = mWrapper.getSharedPreferences("Perference1",
                ContextWrapper.MODE_PRIVATE);
    }

    public void setLogin(Boolean login) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean("login", login);
        editor.commit();
        isLogin = login;
    }
    public void setUser(User user){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt("userId", user.getUserId());
        editor.putString("phone",user.getPhone());
        editor.putString("password",user.getPassword());
        editor.putInt("school_id",user.getSchool_id());
        editor.putString("name",user.getName());
        editor.putBoolean("sex",user.getSex());
        editor.putString("student_num",user.getStudentNum());
        editor.putString("head_img",user.getHead_image());
        editor.putString("nick_name",user.getNick_name());
        editor.commit();
        this.user=user;
    }
    public void setNickName(String nickName){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString("nick_name",nickName);
        editor.commit();
        user.setNick_name(nickName);
    }
    public void setSex(Boolean sex){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean("sex",sex);
        editor.commit();
        user.setSex(sex);
    }
    public void setHeadImage(String url){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString("head_img",url);
        editor.commit();
        user.setHead_image(url);
    }
    public void setPassword(String password){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString("password",password);
        editor.commit();
        user.setPassword(password);
    }

    public User getUser() {
        return user;
    }
    public Boolean getLogin() {
        return isLogin;
    }
}
