package com.mcc.schoolmarket;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.mcc.adpter.GoodItemAdapter;
import com.mcc.data.Good;
import com.mcc.schoolmarket.databinding.ActivitySearchGoodBinding;
import com.mcc.viewModel.SearchGoodVM;

import java.util.ArrayList;
import java.util.List;

public class SearchGoodActivity extends BaseActivity {
    private ActivitySearchGoodBinding binding;
    private SearchGoodVM vm;
    private int class_id;
    private String keyword;
    private List<Good> goodList=new ArrayList<>();
    private GoodItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_search_good);
        class_id=getIntent().getIntExtra("class_id",0);
        if(class_id==0){
            keyword=getIntent().getStringExtra("keyword");
            vm=new SearchGoodVM(keyword,this);
        }else {
            vm=new SearchGoodVM(class_id,this);
        }
        binding.setVm(vm);
        LinearLayoutManager layoutManager=new LinearLayoutManager(mContext);
        binding.goodRecycler.setLayoutManager(layoutManager);
        adapter=new GoodItemAdapter(goodList,mContext);
        binding.goodRecycler.setAdapter(adapter);
        //添加Android自带的分割线
        binding.goodRecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
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
        binding.goodRecycler.refreshComplete();
        if (goods.size()>0) {
            goodList.clear();
            goodList.addAll(goods);
            adapter.notifyDataSetChanged();
        }else {
            binding.goodRecycler.setPullRefreshEnabled(false);
        }
        if(goods.size()<10){
            binding.goodRecycler.setLoadingMoreEnabled(false);
        }
    }
    public void addGoodList(List<Good> goods){
        binding.goodRecycler.loadMoreComplete();
        if(goodList.size()>0){
            goodList.addAll(goods);
            adapter.notifyDataSetChanged();
        }
        if(goods.size()<10){
            binding.goodRecycler.setLoadingMoreEnabled(false);
        }
    }

}
