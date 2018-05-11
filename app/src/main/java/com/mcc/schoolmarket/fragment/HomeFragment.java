package com.mcc.schoolmarket.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ScrollingView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mcc.adpter.MenuBarAdapter;
import com.mcc.app.MyApplication;
import com.mcc.data.MenuBar;
import com.mcc.schoolmarket.AboutSchoolMarketActivity;
import com.mcc.schoolmarket.R;
import com.mcc.schoolmarket.databinding.FragmentHomeBinding;
import com.mcc.scroll.ObservableScrollView;
import com.mcc.tools.MyUtil;
import com.mcc.view.ImageCycleView;
import com.mcc.view.MyListView;
import com.mcc.viewModel.HomeFragmentVM;
import com.mcc.viewModel.LoginVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zw on 2018/5/2.
 */

public class HomeFragment extends Fragment implements ObservableScrollView.ScrollViewListener{
    private Activity activity;
    private View top;
    private ImageCycleView mImageCycleView;//轮播的空间
    private Drawable drawable;//顶部搜索背景
    private FragmentHomeBinding binding;
    private HomeFragmentVM vm;
    private List<MenuBar> menuBarList=new ArrayList<>();
    private  MenuBarAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        activity=getActivity();
        init();
        vm=new HomeFragmentVM(this);
        binding.setVm(vm);
        return binding.getRoot();
    }
    private void init(){
        binding.scrollView.setScrollViewListener(this);
        binding.viewHead.setBackgroundColor(Color.argb(0, 255, 255, 255));
        //顶部搜索背景
        drawable = ContextCompat.getDrawable(getActivity(), R.drawable.main_top_bg_circular);
        drawable.setAlpha((int) 60);
        binding.gpRelSearch.setBackground(drawable);
        // 获取顶部图片高度后，设置滚动监听
        ViewTreeObserver vto = binding.rlbannerView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                binding.rlbannerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                binding.rlbannerView.getWidth();
            }
        });
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);
        binding.menuListView.setLayoutManager(layoutManager);
        adapter=new MenuBarAdapter(menuBarList,activity);
        binding.menuListView.setAdapter(adapter);
        String[] data={"A","B","C","D","E","F","G","H","I","J","K","L","M","N"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(activity,android.R.layout.simple_list_item_1,data);
        binding.mylistview.setAdapter(adapter);
    }
    //增加横幅的view
    public void addview(List<ImageCycleView.ImageInfo> list) {
        if (top != null || list.size() > 0) {
            System.out.println("NSJ REMOVE VIEW");
            binding.rlbannerView.removeView(top);
            top = null;
        }

        //轮播数据
        LayoutInflater inflater1 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        top = inflater1.inflate(R.layout.view_change, null);
        mImageCycleView = (ImageCycleView) top.findViewById(R.id.icv_topView);
        MyUtil.setViewLayoutParams(mImageCycleView, MyApplication.screenWidth, MyApplication.screenWidth * 450 / 750);
        if (list.size() == 1) {
            mImageCycleView.setAutoCycle(false); //关闭自动播放
        } else {
            mImageCycleView.setAutoCycle(true); //开启自动播放
        }

//			mImageCycleView.setCycleDelayed(2000);//设置自动轮播循环时间
        mImageCycleView.setOnPageClickListener(new ImageCycleView.OnPageClickListener() {
            @Override
            public void onClick(View imageView, ImageCycleView.ImageInfo imageInfo) {
                Intent intent=new Intent(activity, AboutSchoolMarketActivity.class);
                startActivity(intent);
            }
        });
        mImageCycleView.loadData(list, new ImageCycleView.LoadImageCallBack() {
            @Override
            public ImageView loadAndDisplay(ImageCycleView.ImageInfo imageInfo) {
                ImageView imageView = new ImageView(getActivity());
                //加载本地
                imageView.setImageResource(Integer.parseInt(imageInfo.image.toString()));
                //加载网络
                //  ImageLoader.getInstance().displayImage(imageInfo.image.toString(), imageView, ToolsClass.Defaultoptions);
                return imageView;
            }
        });
        Log.i("NSJ ADD VIEW", "TOP");
        binding.rlbannerView.addView(top);

    }
    public void setMenuList(List<MenuBar> list){
        menuBarList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        int height = MyApplication.screenWidth * 450 / (750 * 2);
        if (y <= height) {//banner高度的一半
            float scale = (float) y / height;
            float alpha = (255 * scale);
            binding.viewHead.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));

            if (alpha < 60) {
                alpha = 60;
            } else {
                alpha = 30;
            }
            drawable.setAlpha((int) alpha);
            binding.gpRelSearch.setBackground(drawable);
            binding.imSearch.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.white_search));
            binding.tvHomeSearch.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            binding.tvHomeSearch.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        } else {
            binding.imSearch.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.searchbox_icon));
            binding.tvHomeSearch.setTextColor(ContextCompat.getColor(getActivity(), R.color.search_text_gray));
            binding.tvHomeSearch.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.search_text_gray));
        }
    }
}
