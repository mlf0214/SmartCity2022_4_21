package com.example.myapplication.ui.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.bean.DjBean;
import com.example.myapplication.ui.base.BaseActivity;
import com.example.myapplication.util.MyBaseAdapter;
import com.example.myapplication.util.ViewHolde;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ZzhdActivity extends BaseActivity {
    private android.widget.RelativeLayout mToolbar;
    private android.widget.ImageView mLeftIcon;
    private android.widget.TextView mTitle;
    private android.widget.ImageView mRightIcon;
    private com.example.myapplication.util.MyListView mMlv;
    private android.widget.Button mBtn1;
    private android.widget.EditText mEt;
    private android.widget.Button mBtn2;
    private int flag=-1;

    @Override
    protected void initView() {

        mToolbar = findViewById(R.id.toolbar);
        mLeftIcon = findViewById(R.id.left_icon);
        mTitle = findViewById(R.id.title);
        mRightIcon = findViewById(R.id.right_icon);
        mMlv = findViewById(R.id.mlv);
        mBtn1 = findViewById(R.id.btn1);
        mEt = findViewById(R.id.et);
        mBtn2 = findViewById(R.id.btn2);
        mTitle.setText("组织活动");
        mLeftIcon.setImageResource(R.drawable.ic_baseline_chevron_left_24);
    }
private   List<DjBean> list=new ArrayList<>();;
    @Override
    protected void initData() {

        int[] imgs=new int[]{R.drawable.item1,R.drawable.item2,R.drawable.item3};
        for (int i = 0; i < 8; i++) {
            DjBean djBean=new DjBean();
            djBean.setWztitle("习近平下乡活动");
            djBean.setPlconten("党在我心，一切跟党走，坚定党的路线不动摇");
            djBean.setImg(imgs[new Random().nextInt(3)]);
            djBean.setWzpinumber(String.valueOf(new Random().nextInt(20)+20));
            djBean.setWzgkrenshu(String.valueOf(new Random().nextInt(10)+20));
            list.add(djBean);
        }
        setMlv();
    }

    private void setMlv() {
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
                Glide.with(context).load(djBean.getImg()).into(mIv);
                mTv1.setText(djBean.getWztitle());
                mTv2.setTextColor(Color.BLACK);
                mTv2.setText(djBean.getPlconten());
                mTv2.setTextSize(20);
                mTv3.setText("评论人数:"+djBean.getWzpinumber());
                mTv4.setText("点赞人数:"+djBean.getWzgkrenshu());
                View contentView = viewHolde.getContentView();
                setViewbg(contentView,i);
            }
        });
    }
private boolean isxz=false;
    private void setViewbg(View contentView, int i) {
        if (flag==i) {
            contentView.setBackgroundColor(Color.CYAN);
        }else {
            contentView.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    protected void initEvent() {
   mLeftIcon.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   finish();
               }
           });

   mMlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
       @Override
       public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
           flag=i;
           setMlv();
           isxz=true;
       }
   });
   mBtn1.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           if (isxz){
               Toast.makeText(context, "报名成功", Toast.LENGTH_SHORT).show();
           }else {
               Toast.makeText(context, "请选择要报名的活动", Toast.LENGTH_SHORT).show();
           }
       }
   });
   mBtn2.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           if (isxz){
               if (mEt.getText().toString().equals("")){
                   Toast.makeText(context, "请输入留言内容", Toast.LENGTH_SHORT).show();
               }else {
                   mEt.setText("");
                   Toast.makeText(context, "留言成功", Toast.LENGTH_SHORT).show();
               }
           }else {
               Toast.makeText(context, "请选择要留言的活动", Toast.LENGTH_SHORT).show();
           }
       }
   });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zzhd;
    }
}
