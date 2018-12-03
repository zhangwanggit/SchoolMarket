package com.mcc.adpter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mcc.app.MyApplication;
import com.mcc.data.GoodReply2Reply;
import com.mcc.data.TrendReply2Reply;
import com.mcc.schoolmarket.R;
import com.mcc.viewModel.GoodDetailVM;
import com.mcc.viewModel.TrendDetailVM;

import java.util.List;

/**
 * Created by zw on 2018/5/18.
 */

public class TrendReply2ReplyAdapter extends RecyclerView.Adapter<GoodReply2ReplyAdapter.ViewHolder>{
    private List<TrendReply2Reply> mList;
    private int color;
    private TrendDetailVM vm;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView content;
        public ViewHolder(View view){
            super(view);
            content=(TextView)view.findViewById(R.id.reply_content);
        }
    }
    public TrendReply2ReplyAdapter(List<TrendReply2Reply> list, TrendDetailVM vm){
        mList=list;
        this.vm=vm;
        color= ContextCompat.getColor(MyApplication.getContext(),R.color.themeColor);
    }

    @Override
    public GoodReply2ReplyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.reply_to_reply_item,parent,false);
        GoodReply2ReplyAdapter.ViewHolder holder=new GoodReply2ReplyAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(GoodReply2ReplyAdapter.ViewHolder holder, final int position) {
        TrendReply2Reply reply=mList.get(position);
        String content=reply.getUser_nick_name()+"回复"+reply.getReply_user_name()+":"+reply.getContent();
        SpannableString string=new SpannableString(content);
        string.setSpan(new ForegroundColorSpan(color),0,reply.getUser_nick_name().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        string.setSpan(new ForegroundColorSpan(color),reply.getUser_nick_name().length()+2,reply.getUser_nick_name().length()+2+reply.getReply_user_name().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.content.setText(string);
        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.showReplyLayout2(mList.get(position).getTrend_reply_id(),mList.get(position).getUser_nick_name());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
