package com.mcc.schoolmarket;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.app.Activity;

import com.mcc.schoolmarket.databinding.ActivityChangePasswordBinding;
import com.mcc.viewModel.ChangePasswordVM;

public class ChangePasswordActivity extends BaseActivity {
    private ActivityChangePasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_change_password);
        ChangePasswordVM vm=new ChangePasswordVM(mContext);
        binding.setVm(vm);
    }

}
