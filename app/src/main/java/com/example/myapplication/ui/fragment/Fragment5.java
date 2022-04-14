package com.example.myapplication.ui.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.ui.activity.LogActivity;
import com.example.myapplication.ui.activity.PsdSetActivity;
import com.example.myapplication.ui.activity.UserSetActivity;
import com.example.myapplication.ui.activity.YjkfActivity;
import com.example.myapplication.ui.base.BaseFragment;

public class Fragment5 extends BaseFragment implements View.OnClickListener {
    private ImageView mLeftIcon;
    private TextView mTitle;
    private ImageView mRightIcon;
    private ImageView mIv;
    private LinearLayout mLl;
    private LinearLayout mLl2;
    private LinearLayout mLl3;
    private Button mBtn;

    @Override
    protected void initView() {

        mLeftIcon = getView().findViewById(R.id.left_icon);
        mTitle = getView().findViewById(R.id.title);
        mRightIcon = getView().findViewById(R.id.right_icon);
        mIv = getView().findViewById(R.id.iv);
        mLl = getView().findViewById(R.id.ll);
        mLl2 = getView().findViewById(R.id.ll2);
        mLl3 = getView().findViewById(R.id.ll3);
        mBtn = getView().findViewById(R.id.btn);
          mTitle.setText("个人中心");
    }

    @Override
    protected void initData()    {
        Glide.with(mActivity).load(R.drawable.ic_launcher_foreground)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                                                  .into(mIv);

    }

    @Override
    protected void initEvent() {
        mLl.setOnClickListener(this);
        mLl2.setOnClickListener(this);
        mBtn.setOnClickListener(this);
        mLl3.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fargament5;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll:
                goActivity(UserSetActivity.class);
                break;
            case R.id.ll2:
                goActivity(PsdSetActivity.class);
                break;
            case R.id.ll3:
                goActivity(YjkfActivity.class);
                break;
            case R.id.btn:
                goActivity(LogActivity.class);
                break;
        }
    }
}
