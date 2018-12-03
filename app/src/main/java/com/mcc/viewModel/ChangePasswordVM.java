package com.mcc.viewModel;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.mcc.sharedPreferences.MySharePreferences;
import com.mcc.tools.CallBackListener;
import com.mcc.tools.DatabaseHelper;
import com.mcc.tools.MyUtil;
import com.mcc.tools.ToastUtil;

/**
 * Created by zw on 2018/5/9.
 */

public class ChangePasswordVM {
    private Context mContext;
    public  String old_password;
    public  String new_password;
    public  String confirm_password;
    public ChangePasswordVM(Context context){
        this.mContext=context;
    }

    public String getOld_password() {
        return old_password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }
    public void changePassword(View view){
        //ToastUtil.showShortToast("原密码"+old_password+"新密码"+new_password+"确认密码"+confirm_password);
        if(MyUtil.notNull(old_password,"原密码")&&MyUtil.notNull(old_password,"新密码")&&MyUtil.notNull(old_password,"确认密码")){
            if(!old_password.equals(MySharePreferences.GetInstance(mContext).getUser().getPassword())){
                ToastUtil.showShortToast("原密码错误");
            }else if(!new_password.equals(confirm_password)) {
                ToastUtil.showShortToast("两次输入密码不一致");
            }else {
                MySharePreferences.GetInstance(mContext).setPassword(new_password);
                DatabaseHelper.getInstance(mContext).setUser(MySharePreferences.GetInstance(mContext).getUser(), new CallBackListener() {
                    @Override
                    public void onOK(Object o) {
                        ToastUtil.showShortToast("密码设置成功，请重新登录");
                    }

                    @Override
                    public void onError(String message) {
                        ToastUtil.showShortToast(message);
                    }
                });
            }
        }
    }
    public void finish(View view){
        ((Activity)mContext).finish();
    }
}
