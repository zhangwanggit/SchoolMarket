package com.mcc.viewModel;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mcc.app.MyApplication;
import com.mcc.data.Good;
import com.mcc.data.ItemConfigEntity;
import com.mcc.schoolmarket.R;
import com.mcc.schoolmarket.ReleaseGoodActivity;
import com.mcc.sharedPreferences.MySharePreferences;
import com.mcc.tools.CallBackListener;
import com.mcc.tools.DatabaseHelper;
import com.mcc.tools.MyUtil;
import com.mcc.tools.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zw on 2018/5/11.
 */

public class ReleaseGoodVM {
    private Good good=new Good();
    private Context mContext;
    private ReleaseGoodActivity activity;
    private boolean isNew=true;
    public ReleaseGoodVM(Context context, ReleaseGoodActivity activity){
        this.mContext=context;
        this.activity=activity;
        this.isNew=true;
        findClass();
    }
    public ReleaseGoodVM(Context context,ReleaseGoodActivity activity,int good_id){
        this.mContext=context;
        this.activity=activity;
        this.isNew=false;
        findClass();
        findGood(good_id);
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public void findClass(){
         DatabaseHelper.getInstance(mContext).findClasses(new CallBackListener<List<ItemConfigEntity>>() {
            @Override
            public void onOK(List<ItemConfigEntity> list) {
                activity.addClasses(list);
            }

            @Override
            public void onError(String message) {
                ToastUtil.showShortToast("查询分类出错");
            }
        });

    }
    public void findGood(int good_id){
        DatabaseHelper.getInstance(mContext).findMyGood(good_id, new CallBackListener<Good>() {
            @Override
            public void onOK(Good o) {
               good=o;
                Gson gson = new Gson();
                List<String> imageList=gson.fromJson(o.getImages(),new TypeToken<List<String>>() {}.getType());
                if(imageList!=null&&imageList.size()>0){
                    activity.setImgVid(imageList);
                }

            }

            @Override
            public void onError(String message) {
                ToastUtil.showShortToast(message);
                activity.finish();
            }
        });

    }
    public void chooseClass(View view){
        activity.showClassWindow();
    }
    public void publish(View view){
        List<String> imageList=new ArrayList<>();
        imageList.addAll(activity.getImgVid());
        if(imageList.contains("camera_default")){
            imageList.remove("camera_default");
        }
        if(MyUtil.notNull(good.getTitle(),"标题")&&MyUtil.notNull(good.getClass_id()+"","分类")&&MyUtil.notNull(good.getPrice(),"价格")
                &&MyUtil.notNull(good.getOld_price(),"原价")&&MyUtil.notNull(good.getDescribe(),"描述")){
            if(imageList.size()>0){
                Gson gson = new Gson();
                String images=gson.toJson(imageList);
                good.setImages(images);
                good.setUser_id(MySharePreferences.GetInstance(mContext).getUser().getUserId());
                DatabaseHelper.getInstance(mContext).releaseGood(good, new CallBackListener() {
                    @Override
                    public void onOK(Object o) {
                        ToastUtil.showShortToast("发布成功");
                        activity.finishOK();
                    }

                    @Override
                    public void onError(String message) {
                        ToastUtil.showShortToast("发布失败");
                    }
                });
            }else {
                ToastUtil.showShortToast("请上传商品图片");
            }
        }
    }
    public void update(View view){
        List<String> imageList=new ArrayList<>();
        imageList.addAll(activity.getImgVid());
        if(imageList.contains("camera_default")){
            imageList.remove("camera_default");
        }
        if(MyUtil.notNull(good.getTitle(),"标题")&&MyUtil.notNull(good.getClass_id()+"","分类")&&MyUtil.notNull(good.getPrice(),"价格")
                &&MyUtil.notNull(good.getOld_price(),"原价")&&MyUtil.notNull(good.getDescribe(),"描述")){
            if(imageList.size()>0){
                Gson gson = new Gson();
                String images=gson.toJson(imageList);
                good.setImages(images);
                good.setUser_id(MySharePreferences.GetInstance(mContext).getUser().getUserId());
                DatabaseHelper.getInstance(mContext).updateGood(good, new CallBackListener() {
                    @Override
                    public void onOK(Object o) {
                        ToastUtil.showShortToast("修改成功");
                        activity.finishOK();
                    }

                    @Override
                    public void onError(String message) {
                        ToastUtil.showShortToast("修改失败");
                    }
                });
            }else {
                ToastUtil.showShortToast("请上传商品图片");
            }
        }
    }
}
