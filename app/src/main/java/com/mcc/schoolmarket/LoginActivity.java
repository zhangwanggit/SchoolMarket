package com.mcc.schoolmarket;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mcc.data.User;
import com.mcc.schoolmarket.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;
    private User user=new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_login);
        binding.setPresenter(new Presenter());
        binding.setUser(user);
    }
    public class Presenter{
        //登录
        public void login(View view){
            showShortToast("学号："+user.getStudentNum()+"，密码："+user.getPassword());
           // user.setStudentNum("12014052033");
        }
        //注册
        public void regist(View view){

        }
        public void forgetPw(View view){
            showShortToast("功能正在开发中...");
        }
    }
}
