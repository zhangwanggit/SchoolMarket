package com.mcc.viewModel;

import android.view.View;

import com.mcc.data.Good;
import com.mcc.schoolmarket.MyCollectActivity;
import com.mcc.sharedPreferences.MySharePreferences;
import com.mcc.tools.CallBackListener;
import com.mcc.tools.DatabaseHelper;
import com.mcc.tools.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zw on 2018/5/17.
 */

public class MyCollectVM {
    public MyCollectActivity activity;
    private int userId;
    private int pageIndex;
    public MyCollectVM(MyCollectActivity activity){
        this.activity=activity;
        userId= MySharePreferences.GetInstance(activity).getUser().getUserId();
    }
    public void refreshGood(){
        pageIndex=0;
        DatabaseHelper.getInstance(activity).findMyCollectGoods(pageIndex, userId,new CallBackListener<List<Good>>() {
            @Override
            public void onOK(List<Good> list) {
                activity.setList(list);
            }

            @Override
            public void onError(String message) {
                ToastUtil.showShortToast(message);
            }
        });
    }
    public void loadMoreGood() {
        pageIndex++;
        DatabaseHelper.getInstance(activity).findMyCollectGoods(pageIndex, userId,new CallBackListener<List<Good>>() {
            @Override
            public void onOK(List<Good> list) {
                activity.addGoodList(list);
            }

            @Override
            public void onError(String message) {
                ToastUtil.showShortToast(message);
            }
        });
    }
    public void finish(View view){
        activity.finish();
    }
}
