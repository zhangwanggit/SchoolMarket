package com.mcc.tools;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcc.schoolmarket.R;
import com.mcc.view.RoundAngleImageView;

import java.util.List;

/**
 * Created by zw on 2018/5/5.
 */

public class DataBindingUtil {
    //资源
    @BindingAdapter("imageRes")
    public static void loadImage(ImageView image, int resId){
        image.setImageResource(resId);
    }
    @BindingAdapter("setHeadImg")
    public static void setHeadImg(RoundAngleImageView img,String head_image){
        if(MyUtil.notNull(head_image)){
            Bitmap bitmap=MyUtil.getLoacalBitmap(head_image);
            img.setImageBitmap(bitmap);
        }else {
            img.setImageResource(R.mipmap.school_mark_ic);
        }
    }
    //字体中间加横线
    @BindingAdapter("addCenterLine")
    public static void addCenterLine(TextView textView,String text){
        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }
    //SD卡图片
    @BindingAdapter("imageSD")
    public static void setImageFromSD(ImageView imageView,String url){
        Bitmap bitmap=MyUtil.getLoacalBitmap(url);
        imageView.setImageBitmap(bitmap);
    }
    //设置物品类别图标
    @BindingAdapter("type_image")
    public static void setTypeImage(ImageView image,int type){
        switch (type){
            case 1:
                image.setImageResource(R.drawable.icon_shuma);
                break;
            case 2:
                image.setImageResource(R.drawable.icon_meizhuang);
                break;
            case 3:
                image.setImageResource(R.drawable.icon_fushi);
                break;
            case 4:
                image.setImageResource(R.drawable.icon_dianqi);
                break;
            case 5:
                image.setImageResource(R.drawable.icon_shuji);
                break;
            case 6:
                image.setImageResource(R.drawable.icon_yundong);
                break;
            case 7:
                image.setImageResource(R.drawable.icon_baihuo);
                break;
            case 8:
                image.setImageResource(R.drawable.icon_qita);
                break;
            default:
                image.setImageResource(R.drawable.icon_qita);
                break;
        }
    }
        //设置商品类别名
        @BindingAdapter("type_name")
        public static void setTypeName(TextView textView,int type){
            switch (type){
                case 1:
                    textView.setText("数码");
                    break;
                case 2:
                    textView.setText("美妆");
                    break;
                case 3:
                    textView.setText("服饰");
                    break;
                case 4:
                    textView.setText("电器");
                    break;
                case 5:
                    textView.setText("书籍");
                    break;
                case 6:
                    textView.setText("运动");
                    break;
                case 7:
                    textView.setText("百货");
                    break;
                case 8:
                    textView.setText("其他");
                    break;
                default:
                    textView.setText("");
                    break;

            }
    }

}
