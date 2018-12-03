package com.mcc.viewModel;

import android.content.Context;
import android.view.View;

import com.mcc.adpter.TrendItemAdapter;
import com.mcc.data.Good;
import com.mcc.data.Trend;
import com.mcc.tools.CallBackListener;
import com.mcc.tools.DatabaseHelper;
import com.mcc.tools.ToastUtil;

/**
 * Created by zw on 2018/5/18.
 */

public class TrendItemVM {
    private Context context;
    public Trend trend=new Trend();
    public int position;
    private TrendItemAdapter adapter;
    public TrendItemVM(Context context, Trend trend,int position, TrendItemAdapter adapter,boolean is_self){
        this.context=context;
        this.trend=trend;
        this.position=position;
        this.adapter=adapter;
        trend.setIs_self(is_self);
    }

    public Trend getTrend() {
        return trend;
    }

    public void setTrend(Trend trend) {
        this.trend = trend;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    public void delete(View view){
        DatabaseHelper.getInstance(context).removeTrend(trend.getId(), new CallBackListener() {
            @Override
            public void onOK(Object o) {
                adapter.removeGood(position);
            }

            @Override
            public void onError(String message) {
                ToastUtil.showShortToast("删除失败");
            }
        });

    }
}
