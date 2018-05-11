package com.mcc.schoolmarket;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

import com.mcc.schoolmarket.databinding.ActivitySetSexBinding;
import com.mcc.sharedPreferences.MySharePreferences;
import com.mcc.tools.CallBackListener;
import com.mcc.tools.DatabaseHelper;
import com.mcc.tools.ToastUtil;
import com.mcc.viewModel.MineFragmentVM;

public class SetSexActivity extends BaseActivity {
    private ActivitySetSexBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_set_sex);
        if(MySharePreferences.GetInstance(mContext).getUser().getSex()){
            binding.setIsMan(true);
        }else {
            binding.setIsMan(false);
        }
        binding.backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.rbMan.isChecked()){
                    MySharePreferences.GetInstance(mContext).setSex(true);
                }else{
                    MySharePreferences.GetInstance(mContext).setSex(false);
                }

                DatabaseHelper.getInstance(mContext).setUser(MySharePreferences.GetInstance(mContext).getUser(), new CallBackListener() {
                    @Override
                    public void onOK(Object o) {
                        ToastUtil.showShortToast("性别修改成功");
                        ((Activity) mContext).finish();
                    }

                    @Override
                    public void onError(String message) {
                        ToastUtil.showShortToast(message);
                    }
                });
            }
        });
    }

}
