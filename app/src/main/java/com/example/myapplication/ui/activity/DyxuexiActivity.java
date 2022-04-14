package com.example.myapplication.ui.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.bean.DjBean;
import com.example.myapplication.bean.Mesevent;
import com.example.myapplication.ui.base.BaseActivity;
import com.example.myapplication.util.MyBaseAdapter;
import com.example.myapplication.util.ViewHolde;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class DyxuexiActivity extends BaseActivity {
    private android.widget.ImageView mLeftIcon;
    private android.widget.TextView mTitle;
    private android.widget.ImageView mRightIcon;
    private com.example.myapplication.util.MyListView mMlv;

    @Override
    protected void initView() {

        mLeftIcon = findViewById(R.id.left_icon);
        mTitle = findViewById(R.id.title);
        mRightIcon = findViewById(R.id.right_icon);
        mMlv = findViewById(R.id.mlv);
        mTitle.setText("党员学习");
        mLeftIcon.setImageResource(R.drawable.ic_baseline_chevron_left_24);
    }

    @Override
    protected void initData() {
        setMlvData();
    }

    private void setMlvData() {
        List<DjBean> djBeanList = new ArrayList<>();
        String[] strings = new String[]{"第一章第一节", "第一章第二节", "第二章第三节", "第" +
                "二章第一节", "第二章第二节", "第二章第三节", "第三章第一节", "第三章第二节", "第三章第三节"};
        for (int i = 0; i < strings.length; i++) {
            DjBean djBean = new DjBean();
            djBean.setXxck(strings[i]);
            djBeanList.add(djBean);
        }
        mMlv.setAdapter(new MyBaseAdapter<DjBean>(djBeanList, R.layout.itme, djBeanList.size()) {


            private TextView mTv;

            @Override
            protected void conerview(ViewHolde viewHolde, DjBean djBean, int i) {
                mTv = viewHolde.getView(R.id.tv);
                mTv.setText(djBean.getXxck());
            }
        });
        mMlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Mesevent mesevent=new Mesevent();
                mesevent.setExtName(djBeanList.get(i).getXxck());
                EventBus.getDefault().postSticky(mesevent);
                goActivity(XXKCActivity.class);
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
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dyxx;
    }
}
