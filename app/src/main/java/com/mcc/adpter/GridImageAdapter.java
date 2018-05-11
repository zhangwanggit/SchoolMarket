package com.mcc.adpter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.mcc.schoolmarket.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


/**
 * Created by liuzehua on 2017/8/19.
 */

public class GridImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> dataList;
    private DisplayMetrics dm;
    private LayoutInflater inflater;

    public GridImageAdapter(Context c, ArrayList<String> dataList) {
        this.mContext = c;
        this.dataList = dataList;
        this.dm = new DisplayMetrics();
        inflater = LayoutInflater.from(c);
        ((Activity) mContext).getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
    }

    public void setData(ArrayList<String> datas) {
        this.dataList = datas;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public String getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("L Z H", dataList.get(position));
        convertView = inflater.inflate(R.layout.gv_photoadd_item, null);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.iv_photo);
        ImageView ivx = (ImageView) convertView.findViewById(R.id.iv_x);
        if (dataList != null && position < dataList.size()) {
            if (dataList.get(position).equals("camera_default")) {
                ivPhoto.setImageResource(R.drawable.camera_default);
                ivx.setVisibility(View.INVISIBLE);
            } else {
                    Bitmap bitmap = getLoacalBitmap(dataList.get(position)); //从本地取图片(在cdcard中获取)  //
                    ivPhoto.setImageBitmap(bitmap); //设置Bitmap

            }
        }


        return convertView;
    }

    /**
     * 加载本地图片
     *
     * @param url
     * @return
     */
    public Bitmap getLoacalBitmap(String url) {
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

    public int dipToPx(int dip) {
        return (int) (dip * dm.density + 0.5f);
    }


}
