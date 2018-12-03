package com.mcc.adpter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by zw on 2018/5/5.
 */

public class DataBindingViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    public final T dataBinding;

    public DataBindingViewHolder(T binding) {
        super(binding.getRoot());

        dataBinding = binding;
    }

    public T getDataBinding() {
        return dataBinding;
    }

}
