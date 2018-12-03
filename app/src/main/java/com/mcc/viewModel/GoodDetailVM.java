package com.mcc.viewModel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.mcc.data.Good;
import com.mcc.data.GoodReply;
import com.mcc.data.GoodReply2Reply;
import com.mcc.schoolmarket.GoodDetailActivity;
import com.mcc.sharedPreferences.MySharePreferences;
import com.mcc.tools.CallBackListener;
import com.mcc.tools.DatabaseHelper;
import com.mcc.tools.MyUtil;
import com.mcc.tools.ToastUtil;

import java.util.List;

/**
 * Created by zw on 2018/5/15.
 */

public class GoodDetailVM {
    private Good good=new Good();
    private Context mContext;
    private int good_id;
    public ObservableBoolean showReply=new ObservableBoolean();
    public ObservableField<String> replyContent=new ObservableField<>();
    public ObservableField<String> replyHint=new ObservableField<>();
    private int user_id;
    private int reply_id;//被点击的回复ID
    private String reply_name;//被回复的人名
    private int replyIndex=1;

    private GoodDetailActivity activity;
    public GoodDetailVM(Context context, int good_id, GoodDetailActivity activity){
        this.mContext=context;
        this.good_id=good_id;
        this.activity=activity;
        user_id=MySharePreferences.GetInstance(mContext).getUser().getUserId();
        findGoodDetail();
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }
    public void finish(View view){
        ((Activity)mContext).finish();
    }
    public void share(View view){
        ToastUtil.showShortToast("分享");
    }
    public void findGoodDetail(){
        DatabaseHelper.getInstance(mContext).findGoodDetail(good_id, new CallBackListener<Good>() {
            @Override
            public void onOK(Good o) {
                good=o;
            }

            @Override
            public void onError(String message) {
                ToastUtil.showShortToast(message);
            }
        });
        boolean isCollect=DatabaseHelper.getInstance(mContext).isCollect(good_id,
                MySharePreferences.GetInstance(mContext).getUser().getUserId());
        good.setIs_collected(isCollect);
    }
    public void showReplyLayout(View view){
        replyIndex=1;
        showReply.set(true);
        replyHint.set("请输入回复内容");
        activity.showSoftInputFromWindow();
    }
    public void showReplyLayout2(int reply_id,String reply_name){
        replyIndex=2;
        this.reply_id=reply_id;
        this.reply_name=reply_name;
        showReply.set(true);
        replyHint.set("回复 "+reply_name+":");
        activity.showSoftInputFromWindow();
    }
    public void reply(View view){
        if(MyUtil.notNull(replyContent.get())){
            if(replyIndex==1) {
                DatabaseHelper.getInstance(mContext).goodReply(good_id, replyContent.get(),
                        user_id, new CallBackListener() {
                            @Override
                            public void onOK(Object o) {
                                ToastUtil.showShortToast("回复成功");
                                findReplys();
                                showReply.set(false);
                                activity.hideInput();
                                replyContent.set("");
                            }

                            @Override
                            public void onError(String message) {
                                ToastUtil.showShortToast("回复失败");
                            }
                        });
            }else if(replyIndex==2){
                DatabaseHelper.getInstance(mContext).goodReply2Reply(reply_id, replyContent.get(), user_id, reply_name, new CallBackListener() {
                    @Override
                    public void onOK(Object o) {
                        ToastUtil.showShortToast("回复成功");
                        findReplys();
                        showReply.set(false);
                        activity.hideInput();
                        replyContent.set("");
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
            }else if(replyIndex==3){

            }
        }else {
            ToastUtil.showShortToast("请输入回复内容");
        }
    }
    public void findReplys(){
        List<GoodReply> goodReplies=DatabaseHelper.getInstance(mContext).findReplys(good_id);
        for(int i=0;i<goodReplies.size();i++){
            List<GoodReply2Reply> goodReply2Replies=DatabaseHelper.getInstance(mContext)
                    .findReply2Replys(goodReplies.get(i).getId());
            goodReplies.get(i).setList(goodReply2Replies);
        }
        good.setReply_num(goodReplies.size());
        activity.setReplys(goodReplies);
        //good.setReplies(goodReplies);
    }
    public void call(View view){
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+good.getPhone()));
            mContext.startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void collect(View view){
        if(user_id!=good.getUser_id()) {//非本人
            DatabaseHelper.getInstance(mContext).collect(good_id, user_id, new CallBackListener<String>() {
                @Override
                public void onOK(String message) {
                    ToastUtil.showShortToast(message);
                    if (good.isIs_collected()) {
                        good.setIs_collected(false);
                        good.setCollect_num(good.getCollect_num()-1);
                    } else {
                        good.setIs_collected(true);
                        good.setCollect_num(good.getCollect_num()+1);
                    }
                }

                @Override
                public void onError(String message) {
                    ToastUtil.showShortToast(message);
                }
            });
        }else {
            ToastUtil.showShortToast("不可收藏自己发布的商品");
        }

    }
}
