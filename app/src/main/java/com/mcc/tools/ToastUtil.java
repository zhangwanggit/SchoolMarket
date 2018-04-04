package com.mcc.tools;

import android.app.Application;
import android.widget.Toast;

import com.mcc.app.MyApplication;

/**
 * Created by zw on 2018/4/3.
 */

public class ToastUtil {
    private static Toast mToast = null;
    public  void showShortToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApplication.getContext(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }
    public  void showLongToast(String text){
        if (mToast == null) {
            mToast = Toast.makeText(MyApplication.getContext(), text, Toast.LENGTH_LONG);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.show();
    }
}
