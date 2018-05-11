package com.mcc.viewModel;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.mcc.app.MyApplication;
import com.mcc.data.User;
import com.mcc.schoolmarket.AboutSchoolMarketActivity;
import com.mcc.schoolmarket.FeedBackActivity;
import com.mcc.schoolmarket.LoginActivity;
import com.mcc.schoolmarket.MineAccountActivity;
import com.mcc.schoolmarket.MyReleaseActivity;
import com.mcc.schoolmarket.RegistActivity;
import com.mcc.schoolmarket.databinding.FragmentMineBinding;
import com.mcc.schoolmarket.fragment.MineFragment;
import com.mcc.sharedPreferences.MySharePreferences;

/**
 * Created by zw on 2018/5/7.
 */

public class MineFragmentVM {
    public static User user=new User();
    private Context mContext;
    private FragmentMineBinding mineBinding;
    public MineFragmentVM(Context context, FragmentMineBinding binding){
    this.mContext=context;
    this.mineBinding=binding;
    user=MySharePreferences.GetInstance(mContext).getUser();
    binding.setVm(this);
    }
    //跳转到账户设置界面
    public void goMineAccount(View view){
        Intent intent=new Intent(mContext, MineAccountActivity.class);
        mContext.startActivity(intent);
    }
    public void aboutSchoolMarket(View view){
        Intent intent=new Intent(mContext, AboutSchoolMarketActivity.class);
        mContext.startActivity(intent);
    }
    public void feedBack(View view){
        Intent intent=new Intent(mContext, FeedBackActivity.class);
        mContext.startActivity(intent);
    }
    public void myRelease(View view){
        Intent intent=new Intent(mContext, MyReleaseActivity.class);
        mContext.startActivity(intent);
    }
}
