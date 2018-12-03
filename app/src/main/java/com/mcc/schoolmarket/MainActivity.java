package com.mcc.schoolmarket;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mcc.app.MyApplication;
import com.mcc.schoolmarket.databinding.ActivityMainBinding;
import com.mcc.schoolmarket.fragment.HomeFragment;
import com.mcc.schoolmarket.fragment.MessageFragment;
import com.mcc.schoolmarket.fragment.MineFragment;
import com.mcc.schoolmarket.fragment.SchoolFragment;
import com.mcc.tools.ToastUtil;
import com.mcc.viewModel.LoginVM;
import com.mcc.viewModel.MainVM;


public class MainActivity extends FragmentActivity{
    private FragmentManager fm;
    private FragmentTransaction ft;
    private HomeFragment homeFragment;//首页Fragment
    private SchoolFragment schoolFragment;//校园Fragment
    private MessageFragment messageFragment;//消息Fragment
    private MineFragment mineFragment;//我的Fragment
    private RadioButton homepage;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setVm(new MainVM(this));
        MyApplication.getInstance().addActivity(this);
        fm = getSupportFragmentManager();
        showFragment(0);
    }

    public void showFragment(int i) {
        Log.e("MainActivity", "showFragment: "+i );
        ft = fm.beginTransaction();
        switch (i) {
            case 0://首页
                hideFragments(ft);
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    ft.add(R.id.contet, homeFragment);
                } else {
                    ft.show(homeFragment);
                }
                break;
            case 1://学校
                hideFragments(ft);
                if (schoolFragment == null) {
                    schoolFragment = new SchoolFragment();
                    ft.add(R.id.contet, schoolFragment);
                } else {
                    ft.show(schoolFragment);
                }
                break;
            case 2://消息
                hideFragments(ft);
                if (messageFragment == null) {
                    messageFragment = new MessageFragment();
                    ft.add(R.id.contet, messageFragment);
                } else {
                    ft.show(messageFragment);
                }
                break;
            case 3://我的
                hideFragments(ft);
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    ft.add(R.id.contet, mineFragment);
                } else {
                    ft.show(mineFragment);
                }
                break;

        }
        ft.commitAllowingStateLoss();
    }
    private void hideFragments(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (schoolFragment != null) {
            transaction.hide(schoolFragment);
        }
        if (messageFragment != null) {
            transaction.hide(messageFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeAciity(this);
    }

    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 1000) {//如果两次按键时间间隔大于800毫秒，则不退出
                Toast.makeText(MainActivity.this, "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                firstTime = secondTime;//更新firstTime
                return true;
            } else {
                MyApplication.getInstance().finishAll();//否则退出程序
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
