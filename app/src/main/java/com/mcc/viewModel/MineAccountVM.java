package com.mcc.viewModel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mcc.app.MyApplication;
import com.mcc.data.User;
import com.mcc.schoolmarket.ChangePasswordActivity;
import com.mcc.schoolmarket.LoginActivity;
import com.mcc.schoolmarket.SetNickNameActivity;
import com.mcc.schoolmarket.SetSexActivity;
import com.mcc.schoolmarket.databinding.ActivityMineAccountBinding;
import com.mcc.sharedPreferences.MySharePreferences;
import com.mcc.tools.MyUtil;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;

/**
 * Created by zw on 2018/5/7.
 */

public class MineAccountVM {
    private Context mContext;
    public static User user=new User();
    private ActivityMineAccountBinding mineAccountBinding;
    public MineAccountVM(Context context,ActivityMineAccountBinding binding){
        this.mContext=context;
        this.mineAccountBinding=binding;
        user= MySharePreferences.GetInstance(mContext).getUser();
        mineAccountBinding.setVm(this);
    }
    //设置头像
    public void setHeadImage(View view){
        ImageLoader loader=new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
/*                Bitmap bitmap= MyUtil.getLoacalBitmap(path);
                imageView.setImageBitmap(bitmap);*/
                Glide.with(context).load(path).into(imageView);
            }
        };
        ImgSelConfig config=new ImgSelConfig.Builder(mContext,loader)
                // 是否多选
                .multiSelect(false)
                .btnText("Confirm")
                // 确定按钮背景色
                //.btnBgColor(Color.parseColor(""))
                // 确定按钮文字颜色
                .btnTextColor(Color.WHITE)
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#3F51B5"))
                // 返回图标ResId
                .backResId(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material)
                .title("Images")
                .titleColor(Color.WHITE)
                .titleBgColor(Color.parseColor("#3F51B5"))
                .allImagesText("所有图片")
                .needCrop(true)
                .cropSize(1, 1, 200, 200)
                // 第一个是否显示相机
                .needCamera(true)
                // 最大选择图片数量
                .maxNum(9)
                .build();
        ImgSelActivity.startActivity((Activity) mContext, config, 1);
    }
    //退出登录
    public void logOut(View view){
        Intent intent =new Intent(mContext,LoginActivity.class);
        mContext.startActivity(intent);
        MyApplication.getInstance().finishAll();
        MySharePreferences.GetInstance(mContext).setLogin(false);
    }
    //设置昵称
    public void setNickName(View view){
        Intent intent=new Intent(mContext,SetNickNameActivity.class);
        mContext.startActivity(intent);
    }
    //设置性别
    public void setSex(View view){
        Intent intent=new Intent(mContext, SetSexActivity.class);
        mContext.startActivity(intent);
    }
    //修改密码
    public void setPassWord(View view){
        Intent intent=new Intent(mContext, ChangePasswordActivity.class);
        mContext.startActivity(intent);
    }
}
