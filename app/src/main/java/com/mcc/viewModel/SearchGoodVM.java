package com.mcc.viewModel;

import android.databinding.ObservableField;

import com.mcc.data.Good;
import com.mcc.schoolmarket.SearchGoodActivity;
import com.mcc.tools.CallBackListener;
import com.mcc.tools.DatabaseHelper;
import com.mcc.tools.ToastUtil;

import java.util.List;

/**
 * Created by zw on 2018/5/17.
 */

public class SearchGoodVM {
    public ObservableField<String> titleName=new ObservableField<>();
    private int class_id;
    private String keyword;
    private int searchType=0;//0分类查询，1关键字查询
    private int pageIndex=0;
    private SearchGoodActivity activity;
    public SearchGoodVM(int class_id, SearchGoodActivity activity){
        searchType=0;
        this.class_id=class_id;
        this.activity=activity;
        if(class_id==1){
            titleName.set("数码");
        }else if(class_id==2){
            titleName.set("美妆");
        }else if(class_id==3){
            titleName.set("服饰");
        }else if(class_id==4){
            titleName.set("电器");
        }else if(class_id==5){
            titleName.set("书籍");
        }else if(class_id==6){
            titleName.set("运动");
        }else if(class_id==7){
            titleName.set("百货");
        }else if(class_id==8){
            titleName.set("其他");
        }
    }
    public SearchGoodVM(String keyword,SearchGoodActivity activity){
        searchType=1;
        this.keyword=keyword;
        this.activity=activity;
        titleName.set(keyword);
    }
    public void refreshGood(){
        pageIndex=0;
        if(searchType==0){
            DatabaseHelper.getInstance(activity).findGoodsListByClassId(pageIndex, class_id, new CallBackListener<List<Good>>() {
                @Override
                public void onOK(List<Good> list) {
                    activity.setList(list);
                }

                @Override
                public void onError(String message) {
                    ToastUtil.showShortToast(message);
                }
            });
        }else {
            DatabaseHelper.getInstance(activity).findGoodsListByKeyord(pageIndex, keyword, new CallBackListener<List<Good>>() {
                @Override
                public void onOK(List<Good> list) {
                    activity.setList(list);
                }

                @Override
                public void onError(String message) {
                    ToastUtil.showShortToast(message);
                }
            });
        }

    }
    public void loadMoreGood() {
        pageIndex++;
        if(searchType==0){
            DatabaseHelper.getInstance(activity).findGoodsListByClassId(pageIndex, class_id, new CallBackListener<List<Good>>() {
                @Override
                public void onOK(List<Good> list) {
                    activity.addGoodList(list);
                }

                @Override
                public void onError(String message) {
                    ToastUtil.showShortToast(message);
                }
            });
        }else {
            DatabaseHelper.getInstance(activity).findGoodsListByKeyord(pageIndex, keyword, new CallBackListener<List<Good>>() {
                @Override
                public void onOK(List<Good> list) {
                    activity.addGoodList(list);
                }

                @Override
                public void onError(String message) {
                    ToastUtil.showShortToast(message);
                }
            });
        }
    }
}
