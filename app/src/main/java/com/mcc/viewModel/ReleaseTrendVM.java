package com.mcc.viewModel;

import android.content.Context;
import android.view.View;

import com.google.gson.Gson;
import com.mcc.data.Good;
import com.mcc.data.Trend;
import com.mcc.schoolmarket.ReleaseGoodActivity;
import com.mcc.schoolmarket.ReleaseTrendActivity;
import com.mcc.sharedPreferences.MySharePreferences;
import com.mcc.tools.CallBackListener;
import com.mcc.tools.DatabaseHelper;
import com.mcc.tools.MyUtil;
import com.mcc.tools.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zw on 2018/5/18.
 */

public class ReleaseTrendVM {
    private Trend trend=new Trend();
    private Context mContext;
    private ReleaseTrendActivity activity;
    public ReleaseTrendVM(Context context, ReleaseTrendActivity activity){
        this.mContext=context;
        this.activity=activity;
    }

    public void publish(View view){
        List<String> imageList=new ArrayList<>();
        imageList.addAll(activity.getImgVid());
        if(imageList.contains("camera_default")){
            imageList.remove("camera_default");
        }
        if(MyUtil.notNull(trend.getDescribe(),"描述")){
            if(imageList.size()>0) {
                Gson gson = new Gson();
                String images = gson.toJson(imageList);
                trend.setImages(images);
            }else {
                trend.setImages("");
            }
            trend.setUser_id(MySharePreferences.GetInstance(mContext).getUser().getUserId());
            DatabaseHelper.getInstance(mContext).releaseTrend(trend, new CallBackListener() {
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
        }

    }

    public Trend getTrend() {
        return trend;
    }

    public void setTrend(Trend trend) {
        this.trend = trend;
    }
}
