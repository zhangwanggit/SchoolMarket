package com.mcc.schoolmarket;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.mcc.data.User;
import com.mcc.schoolmarket.databinding.ActivityLoginBinding;
import com.mcc.viewModel.LoginVM;

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_login);
        binding.setVm(new LoginVM(mContext));
    }
}
