package com.mcc.schoolmarket;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mcc.adpter.DetailImageItemAdapter;
import com.mcc.adpter.GoodReplyItemAdapter;
import com.mcc.data.GoodReply;
import com.mcc.schoolmarket.databinding.ActivityGoodDetailBinding;
import com.mcc.tools.ToastUtil;
import com.mcc.viewModel.GoodDetailVM;

import java.util.ArrayList;
import java.util.List;

public class GoodDetailActivity extends BaseActivity {
    private ActivityGoodDetailBinding binding;
    private GoodDetailVM vm;
    private DetailImageItemAdapter imageItemAdapter;
    private InputMethodManager imm;
    private List<GoodReply> goodReplies=new ArrayList<>();
    private GoodReplyItemAdapter goodReplyItemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_good_detail);
        int good_id=getIntent().getIntExtra("good_id",0);
        vm=new GoodDetailVM(mContext,good_id,this);
        binding.setVm(vm);
        String imgaes=vm.getGood().getImages();
        Gson gson=new Gson();
        List<String> imageList=gson.fromJson(imgaes,new TypeToken<List<String>>() {}.getType());
        imageItemAdapter=new DetailImageItemAdapter(imageList,mContext);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);
        binding.images.setLayoutManager(linearLayoutManager);
        binding.images.setAdapter(imageItemAdapter);
        binding.images.setNestedScrollingEnabled(false);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        LinearLayoutManager layoutManager=new LinearLayoutManager(mContext);
        binding.replys.setLayoutManager(layoutManager);
        goodReplyItemAdapter=new GoodReplyItemAdapter(goodReplies,mContext,vm);
        binding.replys.setAdapter(goodReplyItemAdapter);
        binding.replys.setNestedScrollingEnabled(false);
        vm.findReplys();
    }
    /**
     * EditText获取焦点并显示软键盘
     */
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
                if (isShouldHideInput(binding.llReply, ev)) {
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
    public void setReplys(List<GoodReply> list){
        goodReplies.clear();
        goodReplies.addAll(list);
        goodReplyItemAdapter.notifyDataSetChanged();

    }
    //隐藏键盘
    public void hideInput(){
        if(imm!=null){
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }

}
