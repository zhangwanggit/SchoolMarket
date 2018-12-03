package com.mcc.schoolmarket;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.mcc.app.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zw on 2018/3/24.
 */

public class BaseActivity extends Activity {
    public Context mContext;
    private static Toast mToast = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        Log.d("BaseActivity", "activityName: "+getClass().getSimpleName());
        MyApplication.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeAciity(this);
    }
    public  void showShortToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }
    public  void showLongToast(String text){
        if (mToast == null) {
            mToast = Toast.makeText(mContext, text, Toast.LENGTH_LONG);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.show();
    }
}
