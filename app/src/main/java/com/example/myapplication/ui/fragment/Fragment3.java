package com.example.myapplication.ui.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.bean.DjBean;
import com.example.myapplication.ui.activity.DjBSActiivty;
import com.example.myapplication.ui.activity.DyxuexiActivity;
import com.example.myapplication.ui.activity.JyXcActivity;
import com.example.myapplication.ui.activity.SspActiivty;
import com.example.myapplication.ui.activity.ZzhdActivity;
import com.example.myapplication.ui.base.BaseFragment;
import com.example.myapplication.util.MyBaseAdapter;
import com.example.myapplication.util.MyGridView;
import com.example.myapplication.util.ViewHolde;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;

import java.util.ArrayList;
import java.util.List;

public class Fragment3 extends BaseFragment {
    private String[] servernames = {"党建动态", "党员学习", "组织活动", "建言献策", "随手拍"};
    private ImageView mLeftIcon;
    private TextView mTitle;
    private ImageView mRightIcon;
    private Banner mBanner;
    private MyGridView mMgv;
    private int[] imgs={R.drawable.dj1,R.drawable.daj2,R.drawable.daj3};

    @Override
    protected void initView() {
        //1.党建展示：首页幻灯片轮播图显示，功能入口等；
        //2.党建动态：首页设置党建动态功能入口，包括文章展示、文章分类、文章评论等；
        //3.党员学习：首页设置党员学习功能入口，包括课程分类、章节管理、学习课程记录标识、课程评论等，课程内容包括语音、视频等资源；
        //4.组织活动：首页设置组织活动功能入口，包括活动展示、活动报名、活动留言等；
        //5.建言献策：首页设置建言献策功能入口，点击进入建言献策页面，页面包括提交问题和历史提交记录，提交问题页面提供用户可编辑的字段有主题、姓名、手机号、内容；
        //6.随手拍：首页设置随手拍功能入口，点击进入随手拍页面，包括随手拍照上传、文章标题、内容。发现身边的先进，反馈身边的问题，实现人人监督。
        mLeftIcon =  getView().findViewById(R.id.left_icon);
        mTitle =     getView().findViewById(R.id.title);
        mRightIcon = getView().findViewById(R.id.right_icon);
        mBanner =    getView().findViewById(R.id.banner);
        mMgv =       getView().findViewById(R.id.mgv);
        mTitle.setText("智慧党建");
    }

    @Override
    protected void initData() {
        setBanner();
        setServer();
    }

    private void setBanner() {
        List<DjBean> djBeanList=new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            DjBean djBean=new DjBean();
            djBean.setImg(imgs[i]);
            djBeanList.add(djBean);
        }


        mBanner.setAdapter(new BannerImageAdapter<DjBean>(djBeanList) {
            @Override
            public void onBindView(BannerImageHolder bannerImageHolder, DjBean djBean, int i, int i1) {
                Glide.with(mActivity).load(djBean.getImg())
                                                          .into(bannerImageHolder.imageView);
            }
        }).start();
    }

    private void setServer() {
        List<DjBean> djBeanList = new ArrayList<>();
        for (int i = 0; i < servernames.length; i++) {
            DjBean djBean = new DjBean();
            djBean.setSevername(servernames[i]);
            djBean.setImg(R.drawable.ic_launcher_foreground);
            djBeanList.add(djBean);
        }
        mMgv.setAdapter(new MyBaseAdapter<DjBean>(djBeanList, R.layout.home_server, djBeanList.size()) {
            private TextView mTv;
            private ImageView mIcon;
            @Override
            protected void conerview(ViewHolde viewHolde, DjBean djBean, int i) {
                mIcon = viewHolde.getView(R.id.icon);
                mTv = viewHolde.getView(R.id.tv);
                mIcon.setMinimumWidth(x / 7);
                mIcon.setMinimumHeight(x / 7);
                Glide.with(mActivity).load(djBean.getImg()).into(mIcon);
                mTv.setText(djBean.getSevername());
            }
        });
        mMgv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        goActivity(DjBSActiivty.class);
                        break;
                    case 1:
                        goActivity(DyxuexiActivity.class);
                        break;
                    case 2:
                        goActivity(ZzhdActivity.class);
                        break;
                    case 3:
                        goActivity(JyXcActivity.class);
                        break;
                    case 4:
                        goActivity(SspActiivty.class);
                        break;
                }
            }
        });
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fargament3;
    }
}
