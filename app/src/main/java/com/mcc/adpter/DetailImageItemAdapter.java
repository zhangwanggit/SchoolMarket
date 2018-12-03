package com.mcc.adpter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mcc.schoolmarket.R;
import com.mcc.schoolmarket.databinding.DetailImageItemBinding;
import com.mcc.schoolmarket.databinding.ImageItemBinding;

import java.util.List;

/**
 * Created by zw on 2018/5/16.
 */

public class DetailImageItemAdapter  extends  RecyclerView.Adapter<DataBindingViewHolder> {
    private List<String> mList;
    private Context mContext;

    public DetailImageItemAdapter(List<String> list, Context context) {
        this.mList = list;
        this.mContext = context;
    }

    @Override
    public DataBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DetailImageItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.detail_image_item, parent, false);
        return new DataBindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(DataBindingViewHolder holder, final int position) {
        DetailImageItemBinding binding = (DetailImageItemBinding) holder.getDataBinding();
        binding.setImage(mList.get(position));
        binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
