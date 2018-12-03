package com.mcc.schoolmarket.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mcc.app.MyApplication;
import com.mcc.schoolmarket.LoginActivity;
import com.mcc.schoolmarket.MainActivity;
import com.mcc.schoolmarket.R;
import com.mcc.schoolmarket.databinding.FragmentMineBinding;
import com.mcc.sharedPreferences.MySharePreferences;
import com.mcc.viewModel.MineFragmentVM;

/**
 * Created by zw on 2018/5/2.
 */

public class MineFragment extends Fragment {
    private View v;
    private FragmentMineBinding binding;
    private MineFragmentVM vm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_mine,container,false);
        vm=new MineFragmentVM(getActivity(),binding);
        return binding.getRoot();
    }
}
