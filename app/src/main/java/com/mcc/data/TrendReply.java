package com.mcc.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zw on 2018/5/18.
 */

public class TrendReply {
    private int id;
    private int userId;//回复者ID
    private int goodId;//商品ID
    private String content;//回复内容
    private String user_nick_name;
    private String user_head_img;
    private String reply_time;
    private boolean user_sex;
    private List<TrendReply2Reply> list=new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGoodId() {
        return goodId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser_nick_name() {
        return user_nick_name;
    }

    public void setUser_nick_name(String user_nick_name) {
        this.user_nick_name = user_nick_name;
    }

    public String getUser_head_img() {
        return user_head_img;
    }

    public void setUser_head_img(String user_head_img) {
        this.user_head_img = user_head_img;
    }

    public String getReply_time() {
        return reply_time;
    }

    public void setReply_time(String reply_time) {
        this.reply_time = reply_time;
    }

    public boolean isUser_sex() {
        return user_sex;
    }

    public void setUser_sex(boolean user_sex) {
        this.user_sex = user_sex;
    }

    public List<TrendReply2Reply> getList() {
        return list;
    }

    public void setList(List<TrendReply2Reply> list) {
        this.list = list;
    }
}
