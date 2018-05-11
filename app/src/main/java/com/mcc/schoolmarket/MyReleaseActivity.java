package com.mcc.schoolmarket;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.mcc.adpter.MyReleaseAdapter;
import com.mcc.data.Good;
import com.mcc.schoolmarket.databinding.ActivityMyReleaseBinding;
import com.mcc.viewModel.MyReleaseVM;

import java.util.ArrayList;
import java.util.List;

public class MyReleaseActivity extends BaseActivity{
    private ActivityMyReleaseBinding binding;
    private MyReleaseVM vm;
    private MyReleaseAdapter adapter;
    private List<Good> goodList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_my_release);
        vm=new MyReleaseVM(this);
        binding.setVm(vm);
        binding.tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vm.setTabIndex(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        LinearLayoutManager layoutManager=new LinearLayoutManager(mContext);
        binding.myGoodRecycler.setLayoutManager(layoutManager);
        adapter=new MyReleaseAdapter(goodList,mContext);
        binding.myGoodRecycler.setAdapter(adapter);
        binding.myGoodRecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                vm.refreshGood();
            }

            @Override
            public void onLoadMore() {
                vm.loadMoreGood();
            }
        });
        vm.refreshGood();
        binding.backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void setGoodList(List<Good> goods){
        binding.myGoodRecycler.refreshComplete();
        if (goods.size()>0) {
            goodList.clear();
            goodList.addAll(goods);
            adapter.notifyDataSetChanged();
        }else {
            binding.myGoodRecycler.setPullRefreshEnabled(false);
        }
        if(goods.size()<10){
            binding.myGoodRecycler.setLoadingMoreEnabled(false);
        }
    }
    public void addGoodList(List<Good> goods){
        binding.myGoodRecycler.loadMoreComplete();
        if(goodList.size()>0){
            goodList.addAll(goods);
            adapter.notifyDataSetChanged();
        }
        if(goods.size()<10){
            binding.myGoodRecycler.setLoadingMoreEnabled(false);
        }
    }

}
