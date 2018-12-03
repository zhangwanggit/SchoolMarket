package com.mcc.adpter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mcc.data.Good;
import com.mcc.schoolmarket.GoodDetailActivity;
import com.mcc.schoolmarket.R;
import com.mcc.schoolmarket.databinding.GoodItemBinding;
import com.mcc.tools.ToastUtil;
import com.mcc.viewModel.GoodItemVM;

import java.util.List;

/**
 * Created by zw on 2018/5/15.
 */

public class GoodItemAdapter extends RecyclerView.Adapter<DataBindingViewHolder>{
    private List<Good> mList;
    private Context mContext;
    public GoodItemAdapter(List<Good> list, Context context){
        this.mList=list;
        this.mContext=context;
    }
    @Override
    public DataBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GoodItemBinding binding= DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.good_item, parent, false);
        return new DataBindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(DataBindingViewHolder holder, final int position) {
        GoodItemBinding binding=(GoodItemBinding) holder.getDataBinding();
        binding.setVm(new GoodItemVM(mContext,mList.get(position)));
        binding.executePendingBindings();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        String imgaes=mList.get(position).getImages();
        Gson gson=new Gson();
        List<String> imageList=gson.fromJson(imgaes,new TypeToken<List<String>>() {}.getType());
        binding.gooImages.setLayoutManager(linearLayoutManager);
        ImageItemAdapter adapter=new ImageItemAdapter(imageList,mContext);
        binding.gooImages.setAdapter(adapter);
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, GoodDetailActivity.class);
                intent.putExtra("good_id",mList.get(position).getId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
