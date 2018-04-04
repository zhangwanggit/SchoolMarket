package com.mcc.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zw on 2018/3/29.
 */

public class MyApplication extends Application{
    private static Context context;
    private static MyApplication myApplication;
    public static List<Activity> activities=new ArrayList<>();
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
    public static MyApplication getInstance(){
        if(myApplication==null){
           myApplication= new MyApplication();
        }
        return myApplication;
    }
    public void addActivity(Activity activity){
        activities.add(activity);
    }
    public void removeAciity(Activity activity){
        activities.remove(activity);
    }
    public void finishAll(){
        for(Activity activity:activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
        activities.clear();
    }

}
