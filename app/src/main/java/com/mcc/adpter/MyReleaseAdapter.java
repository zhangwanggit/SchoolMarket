package com.mcc.adpter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcc.data.Good;
import com.mcc.schoolmarket.GoodDetailActivity;
import com.mcc.schoolmarket.MyReleaseActivity;
import com.mcc.schoolmarket.R;
import com.mcc.schoolmarket.ReleaseGoodActivity;
import com.mcc.schoolmarket.databinding.MyGoodItemBinding;
import com.mcc.tools.ToastUtil;
import com.mcc.viewModel.MyGoodItemVM;

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
        binding.setVm(new MyGoodItemVM(mContext,mList.get(position),position,this));
        binding.executePendingBindings();
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

    public void removeGood(int position){
        mList.remove(position);
        notifyDataSetChanged();
    }
    public void updateGood(int position){
        Intent intent=new Intent(mContext, ReleaseGoodActivity.class);
        intent.putExtra("good_id",mList.get(position).getId());
        ((Activity)mContext).startActivityForResult(intent,2);
    }
}
