package com.mcc.schoolmarket;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.mcc.adpter.GoodItemAdapter;
import com.mcc.adpter.MyReleaseAdapter;
import com.mcc.data.Good;
import com.mcc.schoolmarket.databinding.ActivityMyCollectBinding;
import com.mcc.viewModel.MyCollectVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zw on 2018/5/17.
 */

public class MyCollectActivity extends BaseActivity {
    private ActivityMyCollectBinding binding;
    private MyCollectVM vm;
    private List<Good> goodList=new ArrayList<>();
    private GoodItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_my_collect);
        vm=new MyCollectVM(this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(mContext);
        binding.myCollectRecycler.setLayoutManager(layoutManager);
        adapter=new GoodItemAdapter(goodList,mContext);
        binding.myCollectRecycler.setAdapter(adapter);
        //添加Android自带的分割线
        binding.myCollectRecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
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
    public void setList(List<Good> goods){
        binding.myCollectRecycler.refreshComplete();
        if (goods.size()>0) {
            goodList.clear();
            goodList.addAll(goods);
            adapter.notifyDataSetChanged();
        }else {
            binding.myCollectRecycler.setPullRefreshEnabled(false);
        }
        if(goods.size()<10){
            binding.myCollectRecycler.setLoadingMoreEnabled(false);
        }
    }
    public void addGoodList(List<Good> goods){
        binding.myCollectRecycler.loadMoreComplete();
        if(goodList.size()>0){
            goodList.addAll(goods);
            adapter.notifyDataSetChanged();
        }
        if(goods.size()<10){
            binding.myCollectRecycler.setLoadingMoreEnabled(false);
        }
    }
}
