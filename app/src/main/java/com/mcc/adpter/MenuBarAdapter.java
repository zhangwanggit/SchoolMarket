package com.mcc.adpter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mcc.data.MenuBar;
import com.mcc.schoolmarket.R;
import com.mcc.schoolmarket.databinding.GridviewItemBinding;
import com.mcc.tools.ToastUtil;

import java.util.List;

/**
 * Created by zw on 2018/5/5.
 */

public class MenuBarAdapter extends RecyclerView.Adapter<DataBindingViewHolder> {
    private List<MenuBar> mList;
    private Context mContext;
    public MenuBarAdapter(List<MenuBar> list, Context context){
        this.mList=list;
        this.mContext=context;
    }
    @Override
    public DataBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GridviewItemBinding binding=DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.gridview_item, parent, false);
        return new DataBindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(DataBindingViewHolder holder, final int position) {
        GridviewItemBinding binding=(GridviewItemBinding) holder.getDataBinding();
        binding.setMenuBar(mList.get(position));
        binding.executePendingBindings();
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShortToast(mList.get(position).getName());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
