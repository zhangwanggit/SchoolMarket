package com.mcc.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.WindowManager;

import com.mcc.tools.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zw on 2018/3/29.
 */

public class MyApplication extends Application{
    private static Context context;
    private static MyApplication myApplication;
    public static List<Activity> activities=new ArrayList<>();
    private DatabaseHelper databaseHelper;
    public static int screenWidth;//屏幕宽度
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        databaseHelper=new DatabaseHelper(context, "SchoolMarket.db");;
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
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
