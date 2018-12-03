package com.mcc.schoolmarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by zw on 2018/4/8.
 */

public class OldLoginActivity extends BaseActivity implements View.OnClickListener{
    private EditText et_studentNum,et_passWord;
    private Button bt_login;
    private TextView tv_forget,tv_regist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_old);
        initView();
        setListener();
    }
    private void initView(){
        et_studentNum=(EditText)findViewById(R.id.et_studentNum);
        et_passWord=(EditText)findViewById(R.id.et_passWord);
        bt_login=(Button)findViewById(R.id.bt_login);
        tv_forget=(TextView)findViewById(R.id.tv_forget);
        tv_regist=(TextView)findViewById(R.id.tv_regist);
    }
    private void setListener(){
        bt_login.setOnClickListener(this);
        tv_forget.setOnClickListener(this);
        tv_regist.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login://登录
                String studentNum=et_studentNum.getText().toString();
                String password=et_passWord.getText().toString();
                showShortToast("学号："+studentNum+"，密码："+password);
                break;
            case R.id.tv_forget://忘记密码
                et_studentNum.setText("12014052033");
                break;
            case R.id.tv_regist://注册
                Intent intent=new Intent(mContext,RegistActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
