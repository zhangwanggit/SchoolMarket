package com.mcc.data;

/**
 * Created by zw on 2018/5/18.
 */

public class TrendReply2Reply {
    private int id;
    private int userId;//回复者ID
    private int trend_reply_id;//商品回复id
    private String content;//回复内容
    private String reply_user_name;//被回复的人的名字
    private String user_nick_name;

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

    public int getTrend_reply_id() {
        return trend_reply_id;
    }

    public void setTrend_reply_id(int trend_reply_id) {
        this.trend_reply_id = trend_reply_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReply_user_name() {
        return reply_user_name;
    }

    public void setReply_user_name(String reply_user_name) {
        this.reply_user_name = reply_user_name;
    }

    public String getUser_nick_name() {
        return user_nick_name;
    }

    public void setUser_nick_name(String user_nick_name) {
        this.user_nick_name = user_nick_name;
    }
}
