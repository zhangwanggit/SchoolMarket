package com.mcc.schoolmarket;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.mcc.sharedPreferences.MySharePreferences;

public class SplashAcctivity extends BaseActivity {
    private AlphaAnimation start_anima;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.activity_splash_acctivity, null);
        setContentView(view);
        initData();
    }
    private void initData() {
        start_anima = new AlphaAnimation(0.3f, 1.0f);
        start_anima.setDuration(1000);
        view.startAnimation(start_anima);
        start_anima.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                redirectTo();
            }
        });
    }
    private void redirectTo(){
        if(MySharePreferences.GetInstance(mContext).getLogin()) {//已登陆，跳转到主界面
            Intent intent = new Intent(mContext, MainActivity.class);
            startActivity(intent);
        }else {//未登录，跳转到登录界面
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
