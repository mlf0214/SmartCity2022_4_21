package com.example.myapplication.ui.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.bean.DjBean;
import com.example.myapplication.ui.base.BaseActivity;
import com.example.myapplication.util.MyBaseAdapter;
import com.example.myapplication.util.ViewHolde;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DjBSActiivty extends BaseActivity {
    private String[] wzfl,wztitle,wzplnwumber,wzgkrenshu;
    private int[] wzimg;
    private android.widget.ImageView mLeftIcon;
    private android.widget.TextView mTitle;
    private android.widget.ImageView mRightIcon;
    private android.widget.RadioGroup mRg;
    private android.widget.RadioButton mRb1;
    private android.widget.RadioButton mRb2;
    private android.widget.RadioButton mRb3;
    private android.widget.RadioButton mRb4;
    private android.widget.RadioButton mRb5;
    private android.widget.RadioButton mRb6;
    private com.example.myapplication.util.MyListView mMlv;


    @Override
    protected void initView() {

        mLeftIcon = findViewById(R.id.left_icon);
        mTitle = findViewById(R.id.title);
        mRightIcon = findViewById(R.id.right_icon);
        mRg = findViewById(R.id.rg);
        mRb1 = findViewById(R.id.rb1);
        mRb2 = findViewById(R.id.rb2);
        mRb3 = findViewById(R.id.rb3);
        mRb4 = findViewById(R.id.rb4);
        mRb5 = findViewById(R.id.rb5);
        mRb6 = findViewById(R.id.rb6);
        mTitle.setText("党建动态");
        mLeftIcon.setImageResource(R.drawable.ic_baseline_chevron_left_24);
        mMlv = findViewById(R.id.mlv);
    }

    @Override
    protected void initData() {
        wzfl=new String[]{"今日党建","党建要闻","党在我心","党团活动","党闻趣事","我爱祖国"};
        wztitle=new String[]{"坚决维护中国共产党的领导,坚持不懈走和平发展路线","只有中共共产党才能就中国，党的领导是历史选择的","习近平最近就建党一百周年发表重要讲话",
                "坚决维护中国共产党的领导,坚持不懈走和平发展路线","只有中共共产党才能就中国，党的领导是历史选择的","习近平最近就建党一百周年发表重要讲话"};
        wzimg= new int[]{R.drawable.item1, R.drawable.item2, R.drawable.item1};
        mRb1.setText(wzfl[0]);
        mRb2.setText(wzfl[1]);
        mRb3.setText(wzfl[2]);
        mRb4.setText(wzfl[3]);
        mRb5.setText(wzfl[4]);
        mRb6.setText(wzfl[5]);
    }

    @Override
    protected void initEvent() {
           mLeftIcon.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           finish();
                       }
                   });
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                RadioButton rb = mRg.findViewById(i);
//                int i1 = mRg.indexOfChild(rb);
                selectMlv();
            }
        });
        mRb1.setChecked(true);

    }

    private void selectMlv() {
        List<DjBean> list=new ArrayList<>();
        for (int i = 0; i < wzfl.length; i++) {
            DjBean djBean=new DjBean();
            djBean.setImg(wzimg[new Random().nextInt(3)]);
            djBean.setWzpinumber(String.valueOf(new Random().nextInt(20)+20));
            djBean.setWztitle(wztitle[new Random().nextInt(6)]);
            djBean.setWzgkrenshu(String.valueOf(new Random().nextInt(15)+20));
            list.add(djBean);
        }
        mMlv.setAdapter(new MyBaseAdapter<DjBean>(list, R.layout.newslist, list.size()) {
            private ImageView mIv;
            private TextView mTv4;
            private TextView mTv3;
            private TextView mTv2;
            private TextView mTv1;
            @Override
            protected void conerview(ViewHolde viewHolde, DjBean djBean, int i) {
                mTv1 = viewHolde.getView(R.id.tv1);
                mTv2 = viewHolde.getView(R.id.tv2);
                mTv3 = viewHolde.getView(R.id.tv3);
                mTv4 = viewHolde.getView(R.id.tv4);
                mIv = viewHolde.getView(R.id.iv);
                mTv2.setVisibility(View.GONE);
                Glide.with(context).load(djBean.getImg()).into(mIv);
                mTv1.setText(djBean.getWztitle());
                mTv2.setTextColor(Color.BLACK);
                mTv2.setTextSize(20);
                mTv3.setText("评论人数:"+djBean.getWzpinumber());
                mTv4.setText("点赞人数:"+djBean.getWzgkrenshu());
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_djdt;
    }
}
