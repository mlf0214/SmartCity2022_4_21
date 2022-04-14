package com.example.myapplication.ui.activity;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.ui.base.BaseActivity;
import com.example.myapplication.ui.fragment.Fragment1;
import com.example.myapplication.ui.fragment.Fragment2;
import com.example.myapplication.ui.fragment.Fragment3;
import com.example.myapplication.ui.fragment.Fragment4;
import com.example.myapplication.ui.fragment.Fragment5;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    private android.widget.FrameLayout content;
    private android.widget.LinearLayout tab;
    private android.widget.ImageView tabIv1;
    private android.widget.TextView tabTv1;
    private android.widget.ImageView tabIv2;
    private android.widget.TextView tabTv2;
    private android.widget.ImageView tabIv3;
    private android.widget.TextView tabTv3;
    private android.widget.ImageView tabIv4;
    private android.widget.TextView tabTv4;
    private android.widget.ImageView tabIv5;
    private android.widget.TextView tabTv5;

    @Override
    protected void initView() {
        content = (FrameLayout) findViewById(R.id.content);
        tab =     (LinearLayout) findViewById(R.id.tab);
        tabIv1 = (ImageView) findViewById(R.id.tab_iv1);
        tabTv1 = (TextView) findViewById(R.id.tab_tv1);
        tabIv2 = (ImageView) findViewById(R.id.tab_iv2);
        tabTv2 = (TextView) findViewById(R.id.tab_tv2);
        tabIv3 = (ImageView) findViewById(R.id.tab_iv3);
        tabTv3 = (TextView) findViewById(R.id.tab_tv3);
        tabIv4 = (ImageView) findViewById(R.id.tab_iv4);
        tabTv4 = (TextView) findViewById(R.id.tab_tv4);
        tabIv5 = (ImageView) findViewById(R.id.tab_iv5);
        tabTv5 = (TextView) findViewById(R.id.tab_tv5);
        tabTv1.setClickable(true);
        tabTv2.setClickable(true);
        tabTv3.setClickable(true);
        tabTv4.setClickable(true);
        tabTv5.setClickable(true);
    }
    private void setFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content,fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit();
    }
    @Override
    protected void initData() {
        tabTv1.setText("首页");
        tabTv2.setText("全部服务");
        tabTv3.setText("智慧党建");
        tabTv4.setText("数据分析");
        tabTv5.setText("个人中心");
    }

    @Override
    protected void initEvent() {
        tabIv1 .setOnClickListener(this::onClick);
        tabTv1 .setOnClickListener(this::onClick);
        tabIv2 .setOnClickListener(this::onClick);
        tabTv2 .setOnClickListener(this::onClick);
        tabIv3 .setOnClickListener(this::onClick);
        tabTv3 .setOnClickListener(this::onClick);
        tabIv4 .setOnClickListener(this::onClick);
        tabTv4 .setOnClickListener(this::onClick);
        tabIv5 .setOnClickListener(this::onClick);
        tabTv5 .setOnClickListener(this::onClick);
        setTab(0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tab_iv1:
            case R.id.tab_tv1:
            setTab(0);
            break;
            case R.id.tab_iv2:
            case R.id.tab_tv2:
                setTab(1);
                break;
            case R.id.tab_iv3:
            case R.id.tab_tv3:
                setTab(2);
                break;
            case R.id.tab_iv4:
            case R.id.tab_tv4:
                setTab(3);
                break;
            case R.id.tab_iv5:
            case R.id.tab_tv5:
                setTab(4);
            break;
        }
        tabIv1 = (ImageView) findViewById(R.id.tab_iv1);
        tabTv1 = (TextView) findViewById(R.id.tab_tv1);
        tabIv2 = (ImageView) findViewById(R.id.tab_iv2);
        tabTv2 = (TextView) findViewById(R.id.tab_tv2);
        tabIv3 = (ImageView) findViewById(R.id.tab_iv3);
        tabTv3 = (TextView) findViewById(R.id.tab_tv3);
        tabIv4 = (ImageView) findViewById(R.id.tab_iv4);
        tabTv4 = (TextView) findViewById(R.id.tab_tv4);
        tabIv5 = (ImageView) findViewById(R.id.tab_iv5);
        tabTv5 = (TextView) findViewById(R.id.tab_tv5);
    }

    public void setTab(int i) {
        initTab();
        switch (i){
            case 0:
                tabIv1.setSelected(true);
                tabTv1.setSelected(true);
                setFragment(new Fragment1());
                break;
            case 1:
                tabIv2.setSelected(true);
                tabTv2.setSelected(true);
                setFragment(new Fragment2());
                break;
            case 2:
                tabIv3.setSelected(true);
                tabTv3.setSelected(true);
                setFragment(new Fragment3());

                break;
            case 3:
                tabIv4.setSelected(true);
                tabTv4.setSelected(true);
                setFragment(new Fragment4());
                break;
            case 4:
                tabIv5.setSelected(true);
                tabTv5.setSelected(true);
                setFragment(new Fragment5());
                break;
        }
    }

    private void initTab() {
        tabIv1.setSelected(false);
        tabTv1.setSelected(false);
        tabIv2.setSelected(false);
        tabTv2.setSelected(false);
        tabIv3.setSelected(false);
        tabTv3.setSelected(false);
        tabIv4.setSelected(false);
        tabTv4.setSelected(false);
        tabIv5.setSelected(false);
        tabTv5.setSelected(false);
    }
}