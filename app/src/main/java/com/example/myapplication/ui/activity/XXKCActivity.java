package com.example.myapplication.ui.activity;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.bean.DjBean;
import com.example.myapplication.bean.Mesevent;
import com.example.myapplication.ui.base.BaseActivity;
import com.example.myapplication.util.MyBaseAdapter;
import com.example.myapplication.util.ViewHolde;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class XXKCActivity extends BaseActivity {
    String name;
    private android.widget.ImageView mLeftIcon;
    private android.widget.TextView mTitle;
    private android.widget.ImageView mRightIcon;
    private android.widget.TextView mZjname;
    private android.widget.SeekBar mSk;
    private android.widget.VideoView mVv;
    private com.example.myapplication.util.MyListView mMlv;

    @Override
    protected void initView() {

        mLeftIcon = findViewById(R.id.left_icon);
        mTitle = findViewById(R.id.title);
        mRightIcon = findViewById(R.id.right_icon);
        mZjname = findViewById(R.id.zjname);
        mSk = findViewById(R.id.sk);
        mVv = findViewById(R.id.vv);
        mMlv = findViewById(R.id.mlv);
        mTitle.setText("学习课程");
        mLeftIcon.setImageResource(R.drawable.ic_baseline_chevron_left_24);
    }

    @Override
    protected void initData() {
        mZjname.setText(name);
        mSk.setProgress(new Random().nextInt(100));
//        mVv.setMediaController(new MediaController(this));
//        mVv.setVideoURI(Uri.parse("android:resources://"+getPackageName()+"/"+R.raw.dj2));
//        mVv.start();
        setMlv();
    }

    @Override
    public void setMesg(Mesevent mesg) {
        String extName = mesg.getExtName();
        name=extName;

    }

    private void setMlv() {
        String [] strings=new String[]{"学习了今天这节课，让我收获颇丰","学习了今天这节课让我懂得了我们的祖国是如何强大","通过学习今天这节课，更加的了解了党的历史"};
        List<DjBean> list=new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            DjBean djBean=new DjBean();
            djBean.setXxck(name);
            djBean.setPlconten(strings[new Random().nextInt(3)]);
            djBean.setWzgkrenshu(String.valueOf(new Random().nextInt(20)+20));
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
                Glide.with(context).load(R.drawable.ic_launcher_foreground).into(mIv);
                mTv1.setText(djBean.getXxck());
                mTv2.setTextColor(Color.BLACK);
                mTv2.setText(djBean.getPlconten());
                mTv2.setTextSize(20);
                mTv3.setText("评论人数:"+djBean.getWzpinumber());
                mTv4.setText("点赞人数:"+djBean.getWzgkrenshu());
            }
        });
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_xxck;
    }
}
