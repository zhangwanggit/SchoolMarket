package com.mcc.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 解决scrollView嵌套listView滑动冲突
 * @author Trump
 *
 */
public class MyListView extends ListView {
	  
	public boolean isMeasure;
	
    public MyListView(Context context) {
        // TODO Auto-generated method stub  
        super(context);  
    }  
  
    public MyListView(Context context, AttributeSet attrs) {
        // TODO Auto-generated method stub  
        super(context, attrs);  
    }  
  
    public MyListView(Context context, AttributeSet attrs, int defStyle) {
        // TODO Auto-generated method stub  
        super(context, attrs, defStyle);  
    }  
  
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
        // TODO Auto-generated method stub  
    	isMeasure = true;
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);  
    }  
    
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    	isMeasure = false;
    	super.onLayout(changed, l, t, r, b);
    }    
}