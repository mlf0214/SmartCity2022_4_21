package com.example.myapplication.ui.activity;

import android.view.View;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.bean.Mesevent;
import com.example.myapplication.bean.NewsXqBean;
import com.example.myapplication.bean.User;
import com.example.myapplication.network.OkhttpUtil;
import com.example.myapplication.network.Okhttplim;
import com.example.myapplication.ui.base.BaseActivity;
import com.example.myapplication.ui.base.BaseFragment;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class NewsXqActivity extends BaseActivity {
    private android.widget.ImageView mLeftIcon;
    private android.widget.TextView mTitle;
    private android.widget.ImageView mRightIcon;
    private android.widget.ImageView mIv;
    private android.widget.TextView mTvTitle;
    private android.widget.TextView mTvContent;
    private String newSid;

    @Override
    protected void initView() {

        mLeftIcon = findViewById(R.id.left_icon);
        mTitle = findViewById(R.id.title);
        mRightIcon = findViewById(R.id.right_icon);
        mIv = findViewById(R.id.iv);
        mTvTitle = findViewById(R.id.tv_title);
        mTvContent = findViewById(R.id.tv_content);
        mTitle.setText("新闻详情");
        mLeftIcon.setImageResource(R.drawable.ic_baseline_chevron_left_24);
    }

    @Override
    public void setMesg(Mesevent mesg) {
        String newSID = mesg.getNewSID();
        newSid=newSID;
    }

    @Override
    protected void initData() {
          OkhttpUtil okhttpUtil=new OkhttpUtil();
                    okhttpUtil.setGet(User.URI+"/prod-api/press/press/"+newSid,"");
                    okhttpUtil.setOkhttplim(new Okhttplim() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                        }

                        @Override
                        public void getString(String s) {
                            NewsXqBean newsXqBean=new Gson().fromJson(s,NewsXqBean.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    NewsXqBean.DataDTO data = newsXqBean.getData();
                                    mTvTitle.setText(data.getTitle());
                                    Glide.with(context).load(User.URI+data.getCover())
                                            .into(mIv);
                                    mTvContent.setText(data.getContent());
                                }
                            });
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
        return R.layout.activity_newxq;
    }
}
