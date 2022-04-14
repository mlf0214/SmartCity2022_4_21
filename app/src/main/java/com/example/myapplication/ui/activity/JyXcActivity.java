package com.example.myapplication.ui.activity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.bean.DjBean;
import com.example.myapplication.ui.base.BaseActivity;
import com.example.myapplication.util.MyBaseAdapter;
import com.example.myapplication.util.ViewHolde;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JyXcActivity extends BaseActivity {
    private android.widget.RelativeLayout mToolbar;
    private android.widget.ImageView mLeftIcon;
    private android.widget.TextView mTitle;
    private android.widget.ImageView mRightIcon;
    private android.widget.EditText mEt;
    private android.widget.Button mBtn;
    private com.example.myapplication.util.MyListView mMlv;

    @Override
    protected void initView() {

        mToolbar = findViewById(R.id.toolbar);
        mLeftIcon = findViewById(R.id.left_icon);
        mTitle = findViewById(R.id.title);
        mRightIcon = findViewById(R.id.right_icon);
        mEt = findViewById(R.id.et);
        mBtn = findViewById(R.id.btn);
        mMlv = findViewById(R.id.mlv);
        mTitle.setText("建言献策");
        mLeftIcon.setImageResource(R.drawable.ic_baseline_chevron_left_24);
    }

    @Override
    protected void initData() {

        setmlv();


    }

    private void setmlv() {
        String[] contens = new String[]{"我觉得智慧党建这个平台我没有什么大的问题",
                "我觉得平台对于沟通这方面还有些许不足"};
        String[] names = {"匿名", "张三", "王五", "匿名"};
        List<DjBean> djBeanList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            DjBean djBean = new DjBean();
            djBean.setPlconten(contens[new Random().nextInt(2)]);
            djBean.setXxck(names[new Random().nextInt(4)]);
            djBeanList.add(djBean);
        }
        mMlv.setAdapter(new MyBaseAdapter<DjBean>(djBeanList, R.layout.item2, djBeanList.size()) {
            private TextView mTvContent;
            private TextView mName;

            @Override
            protected void conerview(ViewHolde viewHolde, DjBean djBean, int i) {
                mName =      viewHolde.getView(R.id.name);
                mTvContent = viewHolde.getView(R.id.tv_content);
                mName.setText(djBean.getXxck());
                mTvContent.setText(djBean.getPlconten());
            }
        });

    }

    @Override
    protected void initEvent() {
           mLeftIcon.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           finish();
                       }
                   });
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sumit(mEt.getText().toString());
            }
        });
    }

    private void sumit(String toString) {
        if (toString.equals("")){
            Toast.makeText(context, "请输入内容", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "发表成功", Toast.LENGTH_SHORT).show();
            mEt.setText("");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_jyxc;
    }
}
