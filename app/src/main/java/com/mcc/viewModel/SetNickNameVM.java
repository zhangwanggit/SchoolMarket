package com.mcc.viewModel;

import android.app.Activity;
import android.content.Context;
import android.databinding.Bindable;
import android.view.View;

import com.mcc.sharedPreferences.MySharePreferences;
import com.mcc.tools.CallBackListener;
import com.mcc.tools.DatabaseHelper;
import com.mcc.tools.MyUtil;
import com.mcc.tools.ToastUtil;

/**
 * Created by zw on 2018/5/7.
 */

public class SetNickNameVM {
    public String nick_name="";
    private Context mContext;
    public SetNickNameVM(Context context){
        this.mContext=context;
        this.nick_name= MySharePreferences.GetInstance(mContext).getUser().getNick_name();
    }
    public void save(View view) {
        if (MyUtil.notNull(nick_name)&&nick_name.length()>=2) {
            MySharePreferences.GetInstance(mContext).setNickName(nick_name);
            DatabaseHelper.getInstance(mContext).setUser(MySharePreferences.GetInstance(mContext).getUser(), new CallBackListener() {
                @Override
                public void onOK(Object o) {
                    ToastUtil.showShortToast("昵称修改成功");
                    ((Activity) mContext).finish();
                }

                @Override
                public void onError(String message) {
                    ToastUtil.showShortToast(message);
                }
            });
        }else {
            ToastUtil.showShortToast("昵称不得小于两个字符");
        }
    }

}
