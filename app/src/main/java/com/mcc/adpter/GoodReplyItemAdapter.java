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
import com.mcc.data.GoodReply;
import com.mcc.schoolmarket.GoodDetailActivity;
import com.mcc.schoolmarket.R;
import com.mcc.schoolmarket.databinding.GoodItemBinding;
import com.mcc.schoolmarket.databinding.GoodReplyItemBinding;
import com.mcc.tools.ToastUtil;
import com.mcc.viewModel.GoodDetailVM;
import com.mcc.viewModel.GoodItemVM;

import java.util.List;

/**
 * Created by zw on 2018/5/16.
 */

public class GoodReplyItemAdapter extends RecyclerView.Adapter<DataBindingViewHolder>{
    private List<GoodReply> mList;
    private Context mContext;
    private GoodDetailVM vm;
    public GoodReplyItemAdapter(List<GoodReply> list, Context context, GoodDetailVM vm){
        this.mList=list;
        this.mContext=context;
        this.vm=vm;
    }
    @Override
    public DataBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GoodReplyItemBinding binding= DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.good_reply_item, parent, false);
        return new DataBindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(DataBindingViewHolder holder, final int position) {
        GoodReplyItemBinding binding=(GoodReplyItemBinding) holder.getDataBinding();
        binding.setReply(mList.get(position));
        binding.executePendingBindings();
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               vm.showReplyLayout2(mList.get(position).getId(),mList.get(position).getUser_nick_name());

            }
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);
        binding.reply2replys.setLayoutManager(linearLayoutManager);
        GoodReply2ReplyAdapter adapter=new GoodReply2ReplyAdapter(mList.get(position).getList(),vm);
        binding.reply2replys.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}