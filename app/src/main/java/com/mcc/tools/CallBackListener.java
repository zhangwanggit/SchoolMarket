package com.mcc.tools;

/**
 * Created by zw on 2018/4/28.
 */

public interface CallBackListener<T> {
     void onOK(T t);
     void onError(String message);
}
