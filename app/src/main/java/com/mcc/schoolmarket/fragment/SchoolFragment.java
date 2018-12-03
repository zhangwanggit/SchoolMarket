package com.mcc.schoolmarket.fragment;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.mcc.adpter.TrendItemAdapter;
import com.mcc.data.Trend;
import com.mcc.schoolmarket.R;
import com.mcc.schoolmarket.databinding.FragmentSchoolBinding;
import com.mcc.viewModel.SchoolFragmentVM;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by zw on 2018/5/2.
 */

public class SchoolFragment extends Fragment {
    private Activity activity;
    private FragmentSchoolBinding binding;
    private SchoolFragmentVM vm;
    private TrendItemAdapter trendItemAdapter;
    private List<Trend> trendList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_school,container,false);
        activity=getActivity();
        vm=new SchoolFragmentVM(this);
        binding.setVm(vm);
        init();
        return binding.getRoot();


    }
    public void init(){
        LinearLayoutManager layoutManager1=new LinearLayoutManager(activity);
        binding.trendsRecycler.setLayoutManager(layoutManager1);
        trendItemAdapter=new TrendItemAdapter(trendList,activity);
        binding.trendsRecycler.setAdapter(trendItemAdapter);
        binding.trendsRecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                vm.refresh();
            }

            @Override
            public void onLoadMore() {
                vm.loadMore();
            }
        });
        vm.refresh();
    }
    public void setTrendList(List<Trend> list){
        binding.trendsRecycler.refreshComplete();
        if (list.size()>0) {
            trendList.clear();
            trendList.addAll(list);
            trendItemAdapter.notifyDataSetChanged();
            binding.trendsRecycler.setPullRefreshEnabled(true);
        }else {
            binding.trendsRecycler.setPullRefreshEnabled(false);
        }
        if(list.size()<10){
            binding.trendsRecycler.setLoadingMoreEnabled(false);
        }else {
            binding.trendsRecycler.setLoadingMoreEnabled(true);
        }

    }
    public void addTrendList(List<Trend> list){
        binding.trendsRecycler.loadMoreComplete();
        if(list.size()>0){
            trendList.addAll(list);
            trendItemAdapter.notifyDataSetChanged();
        }
        if(list.size()<10){
            binding.trendsRecycler.setLoadingMoreEnabled(false);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if(resultCode==RESULT_OK) {
                    vm.refresh();
                }
                break;
                default:
                    break;
        }
    }
}
