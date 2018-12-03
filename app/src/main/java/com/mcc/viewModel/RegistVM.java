package com.mcc.viewModel;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import com.mcc.data.User;
import com.mcc.tools.CallBackListener;
import com.mcc.tools.DatabaseHelper;
import com.mcc.tools.MyUtil;
import com.mcc.tools.ToastUtil;

/**
 * Created by zw on 2018/4/30.
 */

public class RegistVM {
    private Context mContext;
    public static User user=new User();
    public DatabaseHelper databaseHelper;//数据库
    public RegistVM(Context context){
        this.mContext=context;
        databaseHelper=DatabaseHelper.getInstance(mContext);
        user.setSchool_id(1);
    }
    //注册
    public void regist(View view){
        Log.e("RegistVM", "昵称: "+user.getNick_name()+"姓名："+user.getName()
                +"手机号："+user.getPhone()+"学校："+user.getSchool_id()+"学号："+user.getStudentNum()
                +"性别："+user.getSex()+"密码："+user.getPassword()+"重复密码"+user.getRePassword());
        if(MyUtil.notNull(user.getNick_name(),"昵称")&&MyUtil.notNull(user.getName(),"姓名")
                &&MyUtil.notNull(user.getPhone(),"手机号")&&MyUtil.notNull(user.getStudentNum(),"学号")
                &&MyUtil.notNull(user.getSex().toString(),"性别")&&MyUtil.notNull(user.getPassword(),"密码")
                &&MyUtil.notNull(user.getRePassword(),"重复密码")){
            if(user.getPassword().equals(user.getRePassword())) {
                databaseHelper.regist(user, new CallBackListener() {
                    @Override
                    public void onOK(Object o) {
                        ToastUtil.showShortToast("注册成功");
                        ((Activity)mContext).finish();
                    }

                    @Override
                    public void onError(String message) {
                        ToastUtil.showShortToast(message);
                    }
                });
            }else {ToastUtil.showShortToast("两次输入密码不一致");}
        }
    }
    public void setSexMan(){
        user.setSex(true);
    }
    public void setSexWoMan(){
        user.setSex(false);
    }
}
