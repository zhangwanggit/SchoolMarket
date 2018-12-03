package com.mcc.viewModel;

import android.content.Context;

import com.mcc.data.Good;

/**
 * Created by zw on 2018/5/15.
 */

public class GoodItemVM {
    private Context context;
    public Good good=new Good();
    public GoodItemVM(Context context,Good good){
        this.context=context;
        this.good=good;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }
}
