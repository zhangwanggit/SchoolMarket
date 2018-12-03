package com.mcc.viewModel;

import android.content.Intent;
import android.view.View;

import com.mcc.data.Trend;
import com.mcc.schoolmarket.ReleaseTrendActivity;
import com.mcc.schoolmarket.fragment.SchoolFragment;
import com.mcc.tools.CallBackListener;
import com.mcc.tools.DatabaseHelper;
import com.mcc.tools.ToastUtil;

import java.util.List;

/**
 * Created by zw on 2018/5/18.
 */

public class SchoolFragmentVM {
    private SchoolFragment fragment;
    private int pageIndex=0;
    public SchoolFragmentVM(SchoolFragment fragment){
        this.fragment=fragment;
    }
    public void refresh(){
        pageIndex=0;
        DatabaseHelper.getInstance(fragment.getContext()).getTrendsList(pageIndex, new CallBackListener<List<Trend>>() {
            @Override
            public void onOK(List<Trend> trends) {
                fragment.setTrendList(trends);
            }

            @Override
            public void onError(String message) {
                ToastUtil.showShortToast(message);
            }
        });
    }
    public void loadMore(){
        pageIndex++;
        DatabaseHelper.getInstance(fragment.getContext()).getTrendsList(pageIndex, new CallBackListener<List<Trend>>() {
            @Override
            public void onOK(List<Trend> trends) {
                fragment.addTrendList(trends);
            }

            @Override
            public void onError(String message) {
                ToastUtil.showShortToast(message);
            }
        });
    }
    public void publish(View view){
        Intent intent=new Intent(fragment.getContext(),ReleaseTrendActivity.class);
        fragment.startActivityForResult(intent,1);
    }
}
