package com.mcc.data;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.mcc.app.MyApplication;
import com.mcc.schoolmarket.BR;
import com.mcc.tools.ToastUtil;

/**
 * Created by zw on 2018/5/9.
 */

public class Good extends BaseObservable{
    private int id;
    private int user_id;//用户ID
    private int class_id;//分类ID
    private String old_price;//原价
    private String price;//现价
    private String title;//标题
    private String describe;//描述
    private String images;//图片
    private int reply_num;//回复数
    private int collect_num;//收藏数
    private int read_num;//阅读量
    private String publishTime;//发布时间
    private int school_id;//学校ID
    private boolean is_deal;//是否已交易
    private boolean is_self;//是否是自己发布的
    private boolean is_collected;//是否已收藏
    private String cover_image;//封面
    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    @Bindable
    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
        notifyPropertyChanged(BR.class_id);
    }

    public String getOld_price() {
        return old_price;
    }

    public void setOld_price(String old_price) {
        this.old_price = old_price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getReply_num() {
        return reply_num;
    }

    public void setReply_num(int reply_num) {
        this.reply_num = reply_num;
    }

    public int getCollect_num() {
        return collect_num;
    }

    public void setCollect_num(int collect_num) {
        this.collect_num = collect_num;
    }

    public int getRead_num() {
        return read_num;
    }

    public void setRead_num(int read_num) {
        this.read_num = read_num;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
    }

    public boolean isIs_deal() {
        return is_deal;
    }

    public void setIs_deal(boolean is_deal) {
        this.is_deal = is_deal;
    }

    public boolean isIs_self() {
        return is_self;
    }

    public void setIs_self(boolean is_self) {
        this.is_self = is_self;
    }

    public boolean isIs_collected() {
        return is_collected;
    }

    public void setIs_collected(boolean is_collected) {
        this.is_collected = is_collected;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }
    public void delete(View view){
        ToastUtil.showShortToast("删除");
    }
    public void complete(View view){
        ToastUtil.showShortToast("完成");
    }
}
