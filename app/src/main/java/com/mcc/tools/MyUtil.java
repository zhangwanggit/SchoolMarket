package com.mcc.tools;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zw on 2018/4/30.
 */

public class MyUtil {
    //判断是否为null或者空
    public static boolean notNull(String str){
        if(str==null||str.equals("null")||str.trim().equals("")){
            return false;
        }else {
            return true;
        }
    }
    //判断是否为null并且 给出提示
    public static boolean notNull(String str,String name){
        if(str==null||str.equals("null")||str.trim().equals("")){
            ToastUtil.showShortToast(name+"不能为空");
            return false;
        }else {
            return true;
        }
    }

    //时间
    public static String getNowStringTime() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }
    public static Long getNowLongTime() {
        return System.currentTimeMillis() / 1000;
    }
    public static String getTimeNow() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new Date());
    }
    public static String getMsgTime(long inTimeInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new Date(inTimeInMillis * 1000));
    }
    /**
     * 设置控件的宽高
     */
    public static void setViewLayoutParams(View view, int width, int height){
        ViewGroup.LayoutParams para = view.getLayoutParams();
        if(width>0){
            para.width = width;
        }
        if(height>0){
            para.height = height;
        }
        view.setLayoutParams(para);
    }

    public static void loadImage(ImageView imageView,String url){
        Bitmap bitmap=getLoacalBitmap(url);
        imageView.setImageBitmap(bitmap);
    }
    /**
     * 加载本地图片
     *
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis, null, options);  ///把流转化为Bitmap图片
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Bitmap getLoacalBitmap2(String url) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            //options.inSampleSize = 4;
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis, null, options);  ///把流转化为Bitmap图片
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static boolean parseBoolean(String s){
        if(s.equals("true")||s.equals("1")){
            return true;
        }
        return false;
    }
}
