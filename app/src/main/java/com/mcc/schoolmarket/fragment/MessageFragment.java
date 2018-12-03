package com.mcc.schoolmarket.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcc.schoolmarket.AboutSchoolMarketActivity;
import com.mcc.schoolmarket.R;

/**
 * Created by zw on 2018/5/2.
 */

public class MessageFragment extends Fragment {
    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_message,null);
        v.findViewById(R.id.iv_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getContext(), AboutSchoolMarketActivity.class);
                startActivity(intent);
            }
        });
        return v;

    }
}
