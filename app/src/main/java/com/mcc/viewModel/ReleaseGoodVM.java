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
import com.mcc.data.Good;
import com.mcc.data.ItemConfigEntity;
import com.mcc.schoolmarket.R;
import com.mcc.schoolmarket.ReleaseGoodActivity;
import com.mcc.tools.CallBackListener;
import com.mcc.tools.DatabaseHelper;
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
    public ReleaseGoodVM(Context context, ReleaseGoodActivity activity){
        this.mContext=context;
        this.activity=activity;
        findClass();
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
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
    public void chooseClass(View view){
        activity.showClassWindow();
    }
    public void publish(View view){
        Log.e("ReleaseGood", "title: "+good.getTitle());
        Log.e("ReleaseGood", "price: "+good.getPrice());
        Log.e("ReleaseGood", "old_price: "+good.getOld_price());
        Log.e("ReleaseGood", "describe: "+good.getDescribe());
        List<String> imageList=activity.getImgVid();
        if(imageList.contains("camera_default")){
            imageList.remove("camera_default");
        }
        Gson gson = new Gson();
        //list转json
        String images=gson.toJson(imageList);
        good.setImages(images);
        Log.e("ReleaseGood", "images: "+images);
        //gson转list
        //List<ItemConfigEntity> list=gson.fromJson(images,new TypeToken<List<ItemConfigEntity>>() {}.getType());


    }
}
