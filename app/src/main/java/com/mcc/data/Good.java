package com.mcc.data;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.mcc.app.MyApplication;
import com.mcc.schoolmarket.BR;
import com.mcc.tools.ToastUtil;

import java.util.ArrayList;
import java.util.List;

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
    private String user_head_img;
    private String user_nick_name;
    private List<GoodReply> replies=new ArrayList<>();
    private boolean user_sex;
    private String phone;
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
    @Bindable
    public int getReply_num() {
        return reply_num;
    }

    public void setReply_num(int reply_num) {
        this.reply_num = reply_num;
        notifyPropertyChanged(BR.reply_num);
    }
    @Bindable
    public int getCollect_num() {
        return collect_num;
    }

    public void setCollect_num(int collect_num) {
        this.collect_num = collect_num;
        notifyPropertyChanged(BR.collect_num);
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
    @Bindable
    public boolean isIs_deal() {
        return is_deal;
    }
    public void setIs_deal(boolean is_deal) {
        this.is_deal = is_deal;
        notifyPropertyChanged(BR.is_deal);
    }

    public boolean isIs_self() {
        return is_self;
    }

    public void setIs_self(boolean is_self) {
        this.is_self = is_self;
    }
    @Bindable
    public boolean isIs_collected() {
        return is_collected;
    }

    public void setIs_collected(boolean is_collected) {
        this.is_collected = is_collected;
        notifyPropertyChanged(BR.is_collected);
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public String getUser_head_img() {
        return user_head_img;
    }

    public void setUser_head_img(String user_head_img) {
        this.user_head_img = user_head_img;
    }

    public String getUser_nick_name() {
        return user_nick_name;
    }

    public void setUser_nick_name(String user_nick_name) {
        this.user_nick_name = user_nick_name;
    }

    public List<GoodReply> getReplies() {
        return replies;
    }

    public void setReplies(List<GoodReply> replies) {
        this.replies = replies;
    }

    public boolean isUser_sex() {
        return user_sex;
    }

    public void setUser_sex(boolean user_sex) {
        this.user_sex = user_sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
