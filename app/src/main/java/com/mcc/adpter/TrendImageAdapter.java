package com.mcc.adpter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mcc.app.MyApplication;
import com.mcc.schoolmarket.R;

import java.util.List;

/**
 * Created by zw on 2018/5/18.
 */

public class TrendImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> mList;
    private Context mContext;

    public TrendImageAdapter(List<String> list, Context context) {
        this.mList = list;
        this.mContext = context;
    }

    static class ViewHolder1 extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder1(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.trend_image1);
        }
    }
    static class ViewHolder2 extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder2(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.trend_image2);
        }
    }
    static class ViewHolder0 extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder0(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.trend_image0);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.size() == 1) {
            return 1;
        } else if (mList.size() == 2) {
            return 2;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType==1){
            view= LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.trend_image_item1,parent,false);
            return new ViewHolder1(view);
        }else if(viewType==2){
            view= LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.trend_image_item2,parent,false);
            return new ViewHolder2(view);
        }else {
            view= LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.trend_image_item_0,parent,false);
            return new ViewHolder0(view);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder1){
            ViewHolder1 holder1=(ViewHolder1)holder;
            Glide.with(MyApplication.getContext()).load(mList.get(position)).into(holder1.imageView);
        }else if(holder instanceof ViewHolder2) {
            ViewHolder2 holder2 = (ViewHolder2) holder;
            Glide.with(MyApplication.getContext()).load(mList.get(position)).into(holder2.imageView);
        }else {
            ViewHolder0 holder0 = (ViewHolder0) holder;
            Glide.with(MyApplication.getContext()).load(mList.get(position)).into(holder0.imageView);
        }

    }
}
