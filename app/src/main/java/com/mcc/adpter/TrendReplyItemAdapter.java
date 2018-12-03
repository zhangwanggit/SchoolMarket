package com.mcc.adpter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcc.data.GoodReply;
import com.mcc.data.TrendReply;
import com.mcc.schoolmarket.R;
import com.mcc.schoolmarket.databinding.GoodReplyItemBinding;
import com.mcc.schoolmarket.databinding.TrendReplyItemBinding;
import com.mcc.viewModel.GoodDetailVM;
import com.mcc.viewModel.TrendDetailVM;

import java.util.List;

/**
 * Created by zw on 2018/5/18.
 */

public class TrendReplyItemAdapter  extends RecyclerView.Adapter<DataBindingViewHolder> {
    private List<TrendReply> mList;
    private Context mContext;
    private TrendDetailVM vm;

    public TrendReplyItemAdapter(List<TrendReply> list, Context context, TrendDetailVM vm) {
        this.mList = list;
        this.mContext = context;
        this.vm = vm;
    }

    @Override
    public DataBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TrendReplyItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.trend_reply_item, parent, false);
        return new DataBindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(DataBindingViewHolder holder, final int position) {
        TrendReplyItemBinding binding = (TrendReplyItemBinding) holder.getDataBinding();
        binding.setReply(mList.get(position));
        binding.executePendingBindings();
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.showReplyLayout2(mList.get(position).getId(), mList.get(position).getUser_nick_name());

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        binding.reply2replys.setLayoutManager(linearLayoutManager);
        TrendReply2ReplyAdapter adapter = new TrendReply2ReplyAdapter(mList.get(position).getList(), vm);
        binding.reply2replys.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
