package com.mcc.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mcc.data.ItemConfigEntity;
import com.mcc.schoolmarket.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by liuzehua on 2017/8/16.
 */

public class InfoReleasePopupAdapter extends BaseAdapter {
    private Context context;
    private List<ItemConfigEntity> listdata=new ArrayList<>();
    private LayoutInflater inflater;
    public InfoReleasePopupAdapter(Context context, List<ItemConfigEntity> listdata){
        this.context=context;
        this.listdata=listdata;
        inflater= LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return listdata.size();
    }

    @Override
    public ItemConfigEntity getItem(int position) {
        return listdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.listview_classify_item,null);
            viewHolder=new ViewHolder();
            viewHolder.tv= (TextView) convertView.findViewById(R.id.tv_type);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.tv.setText(listdata.get(position).getName());
        return convertView;
    }
    class  ViewHolder{
        TextView tv;
    }
}
