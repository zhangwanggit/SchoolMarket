package com.mcc.viewModel;

import com.mcc.schoolmarket.MainActivity;
import com.mcc.tools.ToastUtil;

/**
 * Created by zw on 2018/5/2.
 */

public class MainVM {
    private MainActivity activity;
    private static int HOME=0;
    private static int SCHOOL=1;;
    private static int MESSAGE=2;
    private static int MINE=3;
    public MainVM(MainActivity activity){
        this.activity=activity;
    }
    public void showFragment(int i){
       activity.showFragment(i);
    }

}
