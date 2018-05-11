package com.mcc.schoolmarket;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.mcc.tools.ToastUtil;

public class FeedBackActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        findViewById(R.id.bt_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView feedBack=(TextView) findViewById(R.id.et_feedback);
                if(feedBack.getText().toString().trim().length()>0) {
                    ToastUtil.showShortToast("提交成功");
                    finish();
                }else {
                    ToastUtil.showShortToast("意见反馈不得为空");
                }
            }
        });
        findViewById(R.id.backIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
