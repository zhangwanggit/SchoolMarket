package com.mcc.adpter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mcc.data.Good;
import com.mcc.data.Trend;
import com.mcc.schoolmarket.R;
import com.mcc.schoolmarket.ReleaseGoodActivity;
import com.mcc.schoolmarket.TrendDetailActivity;
import com.mcc.schoolmarket.databinding.MyGoodItemBinding;
import com.mcc.schoolmarket.databinding.TrendItemBinding;
import com.mcc.tools.MyUtil;
import com.mcc.tools.ToastUtil;
import com.mcc.viewModel.MyGoodItemVM;
import com.mcc.viewModel.TrendItemVM;

import java.util.List;
import java.util.Stack;

/**
 * Created by zw on 2018/5/18.
 */

public class TrendItemAdapter extends RecyclerView.Adapter<DataBindingViewHolder> {
    private List<Trend> mList;
    private Context mContext;
    private boolean is_self=false;
    public TrendItemAdapter(List<Trend> list, Context context){
        this.mList=list;
        this.mContext=context;
    }
    public TrendItemAdapter(List<Trend> list, Context context,boolean is_self){
        this.mList=list;
        this.mContext=context;
        this.is_self=true;
    }
    @Override
    public DataBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TrendItemBinding binding= DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.trend_item, parent, false);
        return new DataBindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(DataBindingViewHolder holder, final int position) {
        TrendItemBinding binding=(TrendItemBinding) holder.getDataBinding();
        binding.setVm(new TrendItemVM(mContext,mList.get(position),position,this,is_self));
        binding.executePendingBindings();
        String imgaes=mList.get(position).getImages();
        TrendImageAdapter adapter;
        if(MyUtil.notNull(imgaes)){
            binding.trendImages.setVisibility(View.VISIBLE);
            Gson gson=new Gson();
            List<String> imageList=gson.fromJson(imgaes,new TypeToken<List<String>>() {}.getType());
            StaggeredGridLayoutManager layoutManager;
            if(imageList.size()==1){
                layoutManager=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
            }else if(imageList.size()==2){
                layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
            }else {
                layoutManager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
            }

            binding.trendImages.setLayoutManager(layoutManager);
            adapter=new TrendImageAdapter(imageList,mContext);
            binding.trendImages.setAdapter(adapter);
            binding.trendImages.setNestedScrollingEnabled(false);
            binding.trendImages.setFocusable(false);
        }else {
            binding.trendImages.setVisibility(View.GONE);
        }
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, TrendDetailActivity.class);
                intent.putExtra("trend_id",mList.get(position).getId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void removeGood(int position){
        mList.remove(position);
        notifyDataSetChanged();
    }
}