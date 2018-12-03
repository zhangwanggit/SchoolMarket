package com.mcc.data;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.mcc.schoolmarket.BR;

/**
 * Created by zw on 2018/3/29.
 */

public class User extends BaseObservable{
    private String password;//密码
    private String phone;//手机号
    private String studentNum;//学号
    private int userId;//用户ID
    private String nick_name;//昵称
    private String name;//姓名
    private Boolean sex;//性别
    private int school_id;//学校ID
    private String head_image;//头像
    private String rePassword;//重复密码
    private Long regist_time;//注册时间


    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }
    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }
    @Bindable
    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
        notifyPropertyChanged(BR.studentNum);
    }
    @Bindable
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
        notifyPropertyChanged(BR.userId);
    }
    @Bindable
    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
        notifyPropertyChanged(BR.nick_name);
    }
    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
    @Bindable
    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
        notifyPropertyChanged(BR.sex);
    }
    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
    }
    @Bindable
    public String getHead_image() {
        return head_image;
    }

    public void setHead_image(String head_image) {
        this.head_image = head_image;
        notifyPropertyChanged(BR.head_image);
    }
    @Bindable
    public String getRePassword() {
        return rePassword;
    }
    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
        notifyPropertyChanged(BR.rePassword);
    }

    public Long getRegist_time() {
        return regist_time;
    }

    public void setRegist_time(Long regist_time) {
        this.regist_time = regist_time;
    }
}
