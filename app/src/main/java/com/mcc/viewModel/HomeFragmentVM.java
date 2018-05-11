package com.mcc.viewModel;

import android.content.Context;

import com.mcc.data.MenuBar;
import com.mcc.schoolmarket.R;
import com.mcc.schoolmarket.fragment.HomeFragment;
import com.mcc.view.ImageCycleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zw on 2018/5/5.
 */

public class HomeFragmentVM {
    private HomeFragment fragment;
    private List<ImageCycleView.ImageInfo> list = new ArrayList<ImageCycleView.ImageInfo>();//轮播的list
    private List<MenuBar> menuBarList=new ArrayList<>();//分类菜单list
    public HomeFragmentVM(HomeFragment fragment){
        this.fragment=fragment;
        init();
    }
    private void init(){
        list.add(new ImageCycleView.ImageInfo(R.drawable.banner5,"","",""));
        list.add(new ImageCycleView.ImageInfo(R.drawable.banner1,"","",""));
        list.add(new ImageCycleView.ImageInfo(R.drawable.banner3,"","",""));
        list.add(new ImageCycleView.ImageInfo(R.drawable.banner2,"","",""));
        list.add(new ImageCycleView.ImageInfo(R.drawable.banner4,"","",""));
        fragment.addview(list);
        menuBarList.add(new MenuBar("数码",R.drawable.icon_shuma));
        menuBarList.add(new MenuBar("美妆",R.drawable.icon_meizhuang));
        menuBarList.add(new MenuBar("服饰",R.drawable.icon_fushi));
        menuBarList.add(new MenuBar("电器",R.drawable.icon_dianqi));
        menuBarList.add(new MenuBar("书籍",R.drawable.icon_shuji));
        menuBarList.add(new MenuBar("运动",R.drawable.icon_yundong));
        menuBarList.add(new MenuBar("百货",R.drawable.icon_baihuo));
        menuBarList.add(new MenuBar("其他",R.drawable.icon_qita));
        fragment.setMenuList(menuBarList);

    }

}
