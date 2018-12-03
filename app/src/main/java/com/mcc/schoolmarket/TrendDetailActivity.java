package com.mcc.schoolmarket;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mcc.adpter.GoodReplyItemAdapter;
import com.mcc.adpter.TrendImageAdapter;
import com.mcc.adpter.TrendReplyItemAdapter;
import com.mcc.data.GoodReply;
import com.mcc.data.TrendReply;
import com.mcc.schoolmarket.databinding.ActivityTrendDetailBinding;
import com.mcc.tools.MyUtil;
import com.mcc.viewModel.GoodDetailVM;
import com.mcc.viewModel.TrendDetailVM;

import java.util.ArrayList;
import java.util.List;

public class TrendDetailActivity extends BaseActivity {
    private ActivityTrendDetailBinding binding;
    private InputMethodManager imm;
    private TrendDetailVM vm;
    private TrendReplyItemAdapter trendReplyItemAdapter;
    private List<TrendReply> trendReplies=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_trend_detail);
        int trend_id=getIntent().getIntExtra("trend_id",0);
        vm=new TrendDetailVM(mContext,trend_id,this);
        binding.setVm(vm);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        String imgaes=vm.trend.getImages();
        TrendImageAdapter adapter;
        if(MyUtil.notNull(imgaes)) {
            binding.trendImages.setVisibility(View.VISIBLE);
            Gson gson = new Gson();
            List<String> imageList = gson.fromJson(imgaes, new TypeToken<List<String>>() {
            }.getType());
            StaggeredGridLayoutManager layoutManager;
            if (imageList.size() == 1) {
                layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
            } else if (imageList.size() == 2) {
                layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            } else {
                layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
            }

            binding.trendImages.setLayoutManager(layoutManager);
            adapter = new TrendImageAdapter(imageList, mContext);
            binding.trendImages.setAdapter(adapter);
            binding.trendImages.setNestedScrollingEnabled(false);
            binding.trendImages.setFocusable(false);
        }else {
            binding.trendImages.setVisibility(View.GONE);
        }
        LinearLayoutManager layoutManager=new LinearLayoutManager(mContext);
        binding.replys.setLayoutManager(layoutManager);
        trendReplyItemAdapter=new TrendReplyItemAdapter(trendReplies,mContext,vm);
        binding.replys.setAdapter(trendReplyItemAdapter);
        binding.replys.setNestedScrollingEnabled(false);
        vm.findReplys();
    }
    public void setReplys(List<TrendReply> list){
        trendReplies.clear();
        trendReplies.addAll(list);
        trendReplyItemAdapter.notifyDataSetChanged();

    }

    public void showSoftInputFromWindow() {
        EditText editText=binding.etReply2;
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        imm.showSoftInput(editText,0);
    }
    //点击v以外事件
    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null) {
            int[] leftTop = { 0, 0 };
            v.getLocationInWindow(leftTop);
            int left = leftTop[0], top = leftTop[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            //View v = getCurrentFocus();
            if(vm.showReply.get()){
                if (isShouldHideInput(binding.llReply2repy, ev)) {
                    vm.showReply.set(false);
                    binding.etReply2.setText("");
                    if(hideInputMethod(this, binding.llReply)) {
                        return true; //隐藏键盘时，其他控件不响应点击事件==》注释则不拦截点击事件
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    public static Boolean hideInputMethod(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            return imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        return false;
    }
    public void hideInput(){
        if(imm!=null){
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }
}
