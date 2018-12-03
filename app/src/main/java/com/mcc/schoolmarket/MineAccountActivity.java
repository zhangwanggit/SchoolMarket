package com.mcc.schoolmarket;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

import com.mcc.schoolmarket.databinding.ActivityMineAccountBinding;
import com.mcc.sharedPreferences.MySharePreferences;
import com.mcc.tools.CallBackListener;
import com.mcc.tools.DatabaseHelper;
import com.mcc.tools.ToastUtil;
import com.mcc.viewModel.MineAccountVM;
import com.yuyh.library.imgsel.ImgSelActivity;

import java.util.List;

public class MineAccountActivity extends BaseActivity {
    private ActivityMineAccountBinding binding;
    private MineAccountVM vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_mine_account);
        vm=new MineAccountVM(mContext,binding);
        binding.backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK && data != null) {
                    List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
                    ToastUtil.showShortToast(pathList.get(0));
                    MySharePreferences.GetInstance(mContext).setHeadImage(pathList.get(0));
                    DatabaseHelper.getInstance(mContext).setUser(MySharePreferences.GetInstance(mContext).getUser(), new CallBackListener() {
                        @Override
                        public void onOK(Object o) {
                            ToastUtil.showShortToast("头像设置成功");
                        }

                        @Override
                        public void onError(String message) {
                            ToastUtil.showShortToast(message);
                        }
                    });
                    break;
                }
        }
    }
}
