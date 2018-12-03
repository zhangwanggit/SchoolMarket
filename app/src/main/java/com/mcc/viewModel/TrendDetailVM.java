package com.mcc.viewModel;

import android.app.Activity;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.View;

import com.mcc.data.Good;
import com.mcc.data.GoodReply;
import com.mcc.data.GoodReply2Reply;
import com.mcc.data.Trend;
import com.mcc.data.TrendReply;
import com.mcc.data.TrendReply2Reply;
import com.mcc.schoolmarket.GoodDetailActivity;
import com.mcc.schoolmarket.TrendDetailActivity;
import com.mcc.sharedPreferences.MySharePreferences;
import com.mcc.tools.CallBackListener;
import com.mcc.tools.DatabaseHelper;
import com.mcc.tools.MyUtil;
import com.mcc.tools.ToastUtil;

import java.util.List;

/**
 * Created by zw on 2018/5/18.
 */

public class TrendDetailVM {
    public Trend trend = new Trend();
    private Context mContext;
    private int trend_id;
    public ObservableField<String> replyContent = new ObservableField<>();
    public ObservableField<String> reply2ReplyContent = new ObservableField<>();
    public ObservableField<String> replyHint = new ObservableField<>();
    public ObservableBoolean showReply = new ObservableBoolean();
    private int user_id;
    private int reply_id;//被点击的回复ID
    private String reply_name;//被回复的人名
    private int replyIndex = 1;
    private TrendDetailActivity activity;

    public TrendDetailVM(Context context, int trend_id, TrendDetailActivity activity) {
        this.mContext = context;
        this.trend_id = trend_id;
        this.activity = activity;
        user_id = MySharePreferences.GetInstance(mContext).getUser().getUserId();
        findTrendDetail();
    }

    public void findTrendDetail() {
        DatabaseHelper.getInstance(mContext).findTrendDetail(trend_id, new CallBackListener<Trend>() {
            @Override
            public void onOK(Trend o) {
                trend=o;
            }

            @Override
            public void onError(String message) {
                ToastUtil.showShortToast(message);
            }
        });
        boolean isLiked=DatabaseHelper.getInstance(mContext).isLiked(trend_id,
                MySharePreferences.GetInstance(mContext).getUser().getUserId());
        trend.setIs_liked(isLiked);
    }

    public void finish(View view) {
        ((Activity) mContext).finish();
    }

    public void share(View view) {
        ToastUtil.showShortToast("分享");
    }
    public void like(View view){
        DatabaseHelper.getInstance(mContext).like(trend_id, user_id, new CallBackListener<String>() {
            @Override
            public void onOK(String message) {
                //ToastUtil.showShortToast(message);
                if (trend.isIs_liked()) {
                    trend.setIs_liked(false);
                    trend.setLike_num(trend.getLike_num()-1);
                } else {
                    trend.setIs_liked(true);
                    trend.setLike_num(trend.getLike_num()+1);
                }
            }

            @Override
            public void onError(String message) {
                ToastUtil.showShortToast(message);
            }
        });

    }

    public Trend getTrend() {
        return trend;
    }

    public void setTrend(Trend trend) {
        this.trend = trend;
    }

    public void findReplys() {
        List<TrendReply> trendReplies=DatabaseHelper.getInstance(mContext).findTrendReplys(trend_id);
        for(int i=0;i<trendReplies.size();i++){
            List<TrendReply2Reply> trendReply2Replies=DatabaseHelper.getInstance(mContext)
                    .findTrendReply2Replys(trendReplies.get(i).getId());
            trendReplies.get(i).setList(trendReply2Replies);
        }
        trend.setReply_num(trendReplies.size());
        activity.setReplys(trendReplies);

    }

    public void showReplyLayout2(int reply_id, String reply_name) {
        this.reply_id = reply_id;
        this.reply_name = reply_name;
        showReply.set(true);
        replyHint.set("回复 " + reply_name + ":");
        activity.showSoftInputFromWindow();
    }

    public void reply(View view) {
        if (MyUtil.notNull(replyContent.get())) {
            DatabaseHelper.getInstance(mContext).trendReply(trend_id, replyContent.get(),
                    user_id, new CallBackListener() {
                        @Override
                        public void onOK(Object o) {
                            ToastUtil.showShortToast("回复成功");
                            findReplys();
                            replyContent.set("");
                            activity.hideInput();
                        }

                        @Override
                        public void onError(String message) {
                            ToastUtil.showShortToast("回复失败");
                        }
                    });

        } else {
            ToastUtil.showShortToast("请输入回复内容");
        }
    }

    public void replyToReply(View view) {
        DatabaseHelper.getInstance(mContext).trendReply2Reply(reply_id, reply2ReplyContent.get(), user_id, reply_name, new CallBackListener() {
            @Override
            public void onOK(Object o) {
                ToastUtil.showShortToast("回复成功");
                findReplys();
                replyContent.set("");
                showReply.set(false);
                activity.hideInput();
            }

            @Override
            public void onError(String message) {

            }
        });
    }
}
