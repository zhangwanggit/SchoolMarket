package com.mcc.viewModel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.mcc.data.User;
import com.mcc.schoolmarket.MainActivity;
import com.mcc.schoolmarket.RegistActivity;
import com.mcc.sharedPreferences.MySharePreferences;
import com.mcc.tools.CallBackListener;
import com.mcc.tools.DatabaseHelper;
import com.mcc.tools.MyUtil;
import com.mcc.tools.ToastUtil;

/**
 * Created by zw on 2018/4/4.
 */

public class LoginVM {
    private Context mContext;
    public static User user=new User();
    public DatabaseHelper databaseHelper;//数据库
    public LoginVM(Context context) {
        this.mContext=context;
        databaseHelper=DatabaseHelper.getInstance(mContext);
    }

    //登录
    public void login(View view){
        if(MyUtil.notNull(user.getPhone(),"手机号")&&MyUtil.notNull(user.getPassword(),"密码")) {
            databaseHelper.login(user.getPhone(), user.getPassword(), new CallBackListener<User>() {
                @Override
                public void onOK(User user) {
                    MySharePreferences.GetInstance(mContext).setUser(user);//将用户信息存储到SharePreferences
                    MySharePreferences.GetInstance(mContext).setLogin(true);
                    Intent intent = new Intent(mContext, MainActivity.class);
                    mContext.startActivity(intent);
                    ((Activity)mContext).finish();
                }

                @Override
                public void onError(String message) {
                    ToastUtil.showShortToast(message);//显示错误信息
                }
            });
        }
    }
    //注册
    public void regist(View view){
        Intent intent=new Intent(mContext,RegistActivity.class);
        mContext.startActivity(intent);
    }
   //忘记密码
    public void forgetPw(View view){
        ToastUtil.showShortToast("该功能还未开发");
    }

}
