package com.mcc.viewModel;

import android.content.Context;
import android.view.View;

import com.mcc.adpter.MyReleaseAdapter;
import com.mcc.data.Good;
import com.mcc.schoolmarket.MyReleaseActivity;
import com.mcc.tools.CallBackListener;
import com.mcc.tools.DatabaseHelper;
import com.mcc.tools.ToastUtil;

/**
 * Created by zw on 2018/5/11.
 */

public class MyGoodItemVM {
    private Context context;
    public Good good=new Good();
    public int position;
    public MyReleaseAdapter adapter;
    public MyGoodItemVM(Context context,Good good,int position,MyReleaseAdapter adapter){
        this.context=context;
        this.good=good;
        this.position=position;
        this.adapter=adapter;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    public void delete(final int position){
        DatabaseHelper.getInstance(context).removeGood(good.getId(), new CallBackListener() {
            @Override
            public void onOK(Object o) {
                adapter.removeGood(position);
            }

            @Override
            public void onError(String message) {
                ToastUtil.showShortToast(message);
            }
        });

    }
    public void update(int position){
        adapter.updateGood(position);
    }
    public void complete(int position){
        good.setIs_deal(true);
        DatabaseHelper.getInstance(context).setGoodComplete(good.getId(), new CallBackListener() {
            @Override
            public void onOK(Object o) {
            }

            @Override
            public void onError(String message) {
                ToastUtil.showShortToast(message);
            }
        });

    }
}
