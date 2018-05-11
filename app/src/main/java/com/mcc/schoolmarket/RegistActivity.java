package com.mcc.schoolmarket;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mcc.schoolmarket.databinding.ActivityRegistBinding;
import com.mcc.viewModel.RegistVM;

/**
 * Created by zw on 2018/4/4.
 */

public class RegistActivity extends BaseActivity{
    private ActivityRegistBinding binding;
    private RegistVM viewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_regist);
        viewModel=new RegistVM(this);
        binding.setVm(viewModel);
    }
}
