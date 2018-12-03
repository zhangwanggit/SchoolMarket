package com.mcc.tools;

import android.os.Handler;

import com.andview.refreshview.XRefreshView;
import com.mcc.view.LoadMoreView;


/**
 * Created by karl on 2017/8/16.
 * 刷新的工具类
 */

public class RefreshViewUtil {

    public static void configXRfreshView(final XRefreshView xRefreshView,boolean upload, boolean loadMore, final LoadMoreView loadMoreView) {
        xRefreshView.setPullLoadEnable(true);
        //设置刷新完成以后，headerview固定的时间
        xRefreshView.setPinnedTime(1000);
        xRefreshView.setPullLoadEnable(upload);
        xRefreshView.setPullRefreshEnable(upload);
        xRefreshView.setMoveHeadWhenDisablePullRefresh(upload);
        xRefreshView.setMoveForHorizontal(true);
        xRefreshView.setAutoLoadMore(true);
        xRefreshView.setPullLoadEnable(loadMore);
        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadMoreView.onRefresh();
                    }
                }, 500);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        // 刷新完成必须调用此方法停止加载
                        loadMoreView.onLoadMore();
                    }
                }, 1000);
            }
        });
    }
}
