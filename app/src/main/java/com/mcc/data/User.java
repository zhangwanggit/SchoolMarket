package com.mcc.data;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.mcc.schoolmarket.BR;

/**
 * Created by zw on 2018/3/29.
 */

public class User extends BaseObservable{
    private String userName;//昵称
    private String password;//密码
    private String phone;//手机号
    private String studentNum;//学号

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
