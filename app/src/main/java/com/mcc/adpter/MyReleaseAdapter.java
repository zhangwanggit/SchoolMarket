package com.mcc.adpter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcc.data.Good;
import com.mcc.schoolmarket.R;
import com.mcc.schoolmarket.databinding.MyGoodItemBinding;
import com.mcc.tools.ToastUtil;

import java.util.List;

/**
 * Created by zw on 2018/5/10.
 */

public class MyReleaseAdapter  extends RecyclerView.Adapter<DataBindingViewHolder> {
    private List<Good> mList;
    private Context mContext;
    public MyReleaseAdapter(List<Good> list, Context context){
        this.mList=list;
        this.mContext=context;
    }
    @Override
    public DataBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyGoodItemBinding binding= DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.my_good_item, parent, false);
        return new DataBindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(DataBindingViewHolder holder, final int position) {
        MyGoodItemBinding binding=(MyGoodItemBinding) holder.getDataBinding();
        binding.setGood(mList.get(position));
        binding.executePendingBindings();
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShortToast(mList.get(position).getTitle());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
