package com.mcc.schoolmarket;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.mcc.adpter.MyReleaseAdapter;
import com.mcc.adpter.TrendItemAdapter;
import com.mcc.data.Good;
import com.mcc.data.Trend;
import com.mcc.schoolmarket.databinding.ActivityMyReleaseBinding;
import com.mcc.tools.ToastUtil;
import com.mcc.viewModel.MyReleaseVM;

import java.util.ArrayList;
import java.util.List;

public class MyReleaseActivity extends BaseActivity{
    private ActivityMyReleaseBinding binding;
    private MyReleaseVM vm;
    private MyReleaseAdapter adapter;
    private TrendItemAdapter trendItemAdapter;
    private List<Good> goodList=new ArrayList<>();
    private List<Trend> trendList=new ArrayList<>();
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
        //添加Android自带的分割线
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
        LinearLayoutManager layoutManager1=new LinearLayoutManager(mContext);
        binding.myTrendRecycler.setLayoutManager(layoutManager1);
        trendItemAdapter=new TrendItemAdapter(trendList,mContext,true);
        binding.myTrendRecycler.setAdapter(trendItemAdapter);
        binding.myTrendRecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                vm.refreshTrend();
            }

            @Override
            public void onLoadMore() {
                vm.loadMoreTrend();
            }
        });
        vm.refreshTrend();
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
            binding.myGoodRecycler.setPullRefreshEnabled(true);
        }else {
            binding.myGoodRecycler.setPullRefreshEnabled(false);
        }
        if(goods.size()<10){
            binding.myGoodRecycler.setLoadingMoreEnabled(false);
        }else {
            binding.myGoodRecycler.setLoadingMoreEnabled(true);
        }
    }
    public void addGoodList(List<Good> goods){
        binding.myGoodRecycler.loadMoreComplete();
        if(goods.size()>0){
            goodList.addAll(goods);
            adapter.notifyDataSetChanged();
        }
        if(goods.size()<10){
            binding.myGoodRecycler.setLoadingMoreEnabled(false);
        }
    }

    public void setTrendList(List<Trend> trends){
        binding.myTrendRecycler.refreshComplete();
        if (trends.size()>0) {
            trendList.clear();
            trendList.addAll(trends);
            trendItemAdapter.notifyDataSetChanged();
            binding.myTrendRecycler.setPullRefreshEnabled(true);
        }else {
            binding.myTrendRecycler.setPullRefreshEnabled(false);
        }
        if(trends.size()<10){
            binding.myTrendRecycler.setLoadingMoreEnabled(false);
        }else {
            binding.myTrendRecycler.setLoadingMoreEnabled(true);
        }
    }
    public void addTrendList(List<Trend> trends){
        binding.myTrendRecycler.loadMoreComplete();
        if(trends.size()>0){
            trendList.addAll(trends);
            trendItemAdapter.notifyDataSetChanged();
        }
        if(trends.size()<10){
            binding.myTrendRecycler.setLoadingMoreEnabled(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    vm.refreshGood();
                }
                break;
            case 2:
                if(resultCode==RESULT_OK){
                    vm.refreshTrend();
                }
                break;
        }
    }
}
