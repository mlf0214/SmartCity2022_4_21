package com.example.myapplication.ui.activity;

import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.bean.Mesevent;
import com.example.myapplication.ui.base.BaseActivity;
import com.example.myapplication.util.DateDig;

import org.greenrobot.eventbus.EventBus;

public class BsdateActivty extends BaseActivity {
    private android.widget.RelativeLayout mToolbar;
    private android.widget.ImageView mLeftIcon;
    private android.widget.TextView mTitle;
    private android.widget.ImageView mRightIcon;
    private android.widget.TextView mTvDate;
    private android.widget.Button mBtn1;
    private android.widget.Button mBtn2;

    @Override
    protected void initView() {

        mToolbar = findViewById(R.id.toolbar);
        mLeftIcon = findViewById(R.id.left_icon);
        mTitle = findViewById(R.id.title);
        mRightIcon = findViewById(R.id.right_icon);
        mTvDate = findViewById(R.id.tv_date);
        mBtn1 = findViewById(R.id.btn1);
        mBtn2 = findViewById(R.id.btn2);
        mTitle.setText("时间设置");
        mLeftIcon.setImageResource(R.drawable.ic_baseline_chevron_left_24);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mesevent mesevent1 = EventBus.getDefault().removeStickyEvent(Mesevent.class);
                mesevent1.setDate(mTvDate.getText().toString());
                EventBus.getDefault().postSticky(mesevent1);
                goActivity(BsThirActivity.class);
            }
        });
        mTvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateDig.showDateDig(BsdateActivty.this,mTvDate);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dtdate;
    }
}
