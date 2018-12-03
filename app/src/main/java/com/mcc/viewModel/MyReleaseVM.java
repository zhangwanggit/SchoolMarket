package com.mcc.viewModel;

import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.view.View;

import com.mcc.app.MyApplication;
import com.mcc.data.Good;
import com.mcc.data.Trend;
import com.mcc.schoolmarket.MyReleaseActivity;
import com.mcc.schoolmarket.ReleaseGoodActivity;
import com.mcc.schoolmarket.ReleaseTrendActivity;
import com.mcc.sharedPreferences.MySharePreferences;
import com.mcc.tools.CallBackListener;
import com.mcc.tools.DatabaseHelper;
import com.mcc.tools.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zw on 2018/5/9.
 */

public class MyReleaseVM {
    private MyReleaseActivity activity;
    public ObservableInt tabIndex=new ObservableInt();
    private int goodPage;
    private int trendPage;
    private int userId;
    public MyReleaseVM(MyReleaseActivity activity){
        this.activity=activity;
        userId=MySharePreferences.GetInstance(activity).getUser().getUserId();
        tabIndex.set(0);
    }
    public void finish(View view){
        activity.finish();
    }
    public void publish(View view){
        if(tabIndex.get()==0){
            Intent intent=new Intent(activity, ReleaseGoodActivity.class);
            activity.startActivityForResult(intent,1);
        }else {
            Intent intent=new Intent(activity, ReleaseTrendActivity.class);
            activity.startActivityForResult(intent,2);
        }
    }
    public void setTabIndex(int index){
        tabIndex.set(index);
    }
    public void refreshGood(){
        goodPage=0;
        final List<Good> goods=new ArrayList<>();
        DatabaseHelper.getInstance(activity).getMyGoodsList(userId,goodPage,
                new CallBackListener<List<Good>>() {
                    @Override
                    public void onOK(List<Good> goodsList) {
                        goods.addAll(goodsList);
                    }

                    @Override
                    public void onError(String message) {
                        ToastUtil.showShortToast("查询出错");
                    }
                });
        activity.setGoodList(goods);
    }
    public void loadMoreGood(){
        goodPage++;
        final List<Good> goods=new ArrayList<>();
        DatabaseHelper.getInstance(activity).getMyGoodsList(
                userId,goodPage,
                new CallBackListener<List<Good>>() {
                    @Override
                    public void onOK(List<Good> goodsList) {
                        goods.addAll(goodsList);
                    }

                    @Override
                    public void onError(String message) {
                        ToastUtil.showShortToast("查询出错");
                    }
                });
        activity.addGoodList(goods);
    }
    public void refreshTrend(){
        trendPage=0;
        DatabaseHelper.getInstance(activity).getMyTrendsList(userId, trendPage, new CallBackListener<List<Trend>>() {
            @Override
            public void onOK(List<Trend> list) {
                activity.setTrendList(list);
            }

            @Override
            public void onError(String message) {
                ToastUtil.showShortToast("查询出错");
            }
        });

    }
    public void loadMoreTrend(){
        trendPage++;
        DatabaseHelper.getInstance(activity).getMyTrendsList(userId, trendPage, new CallBackListener<List<Trend>>() {
            @Override
            public void onOK(List<Trend> list) {
                activity.addTrendList(list);
            }

            @Override
            public void onError(String message) {
                ToastUtil.showShortToast("查询出错");
            }
        });
    }
}
