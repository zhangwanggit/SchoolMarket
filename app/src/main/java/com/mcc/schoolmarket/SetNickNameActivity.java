package com.mcc.schoolmarket;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

import com.mcc.schoolmarket.databinding.ActivitySetNickNameBinding;
import com.mcc.viewModel.SetNickNameVM;

public class SetNickNameActivity extends BaseActivity {
    private ActivitySetNickNameBinding binding;
    private SetNickNameVM vm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_set_nick_name);
        vm=new SetNickNameVM(mContext);
        binding.setVm(vm);
        binding.backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
