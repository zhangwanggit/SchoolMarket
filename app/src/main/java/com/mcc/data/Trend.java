package com.mcc.data;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.mcc.schoolmarket.BR;

/**
 * Created by zw on 2018/5/18.
 */

public class Trend extends BaseObservable {
    private int id;
    private int user_id;//用户ID
    private String describe;//描述
    private String images;//图片
    private int reply_num;//回复数
    private int like_num;//点赞数
    private int read_num;//阅读量
    private String publishTime;//发布时间
    private String user_head_img;
    private String user_nick_name;
    private boolean user_sex;
    private boolean is_self;//是否是自己发布的
    private boolean is_liked;//是否已经点赞

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

    public boolean isUser_sex() {
        return user_sex;
    }

    public void setUser_sex(boolean user_sex) {
        this.user_sex = user_sex;
    }

    public boolean isIs_self() {
        return is_self;
    }

    public void setIs_self(boolean is_self) {
        this.is_self = is_self;
    }
    @Bindable
    public boolean isIs_liked() {
        return is_liked;
    }

    public void setIs_liked(boolean is_liked) {
        this.is_liked = is_liked;
        notifyPropertyChanged(BR.is_liked);
    }

    public int getLike_num() {
        return like_num;
    }

    public void setLike_num(int like_num) {
        this.like_num = like_num;
    }
}
