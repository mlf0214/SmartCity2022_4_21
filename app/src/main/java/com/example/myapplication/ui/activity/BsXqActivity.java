package com.example.myapplication.ui.activity;

import android.view.View;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.bean.DTxqBean;
import com.example.myapplication.bean.ImgBean;
import com.example.myapplication.bean.Mesevent;
import com.example.myapplication.bean.User;
import com.example.myapplication.network.OkhttpUtil;
import com.example.myapplication.network.Okhttplim;
import com.example.myapplication.ui.base.BaseActivity;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class BsXqActivity extends BaseActivity {
    private String id;
    private android.widget.RelativeLayout mToolbar;
    private android.widget.ImageView mLeftIcon;
    private android.widget.TextView mTitle;
    private android.widget.ImageView mRightIcon;
    private android.widget.ImageView mIv;
    private android.widget.TextView mTvStrat;
    private android.widget.TextView mTvEnd;
    private android.widget.TextView mTvPrice;
    private android.widget.TextView mTvKm;
    private android.widget.Button mBtn1;
    private android.widget.Button mBtn2;
    private String price,path;

    @Override
    protected void initView() {

        mToolbar = findViewById(R.id.toolbar);
        mLeftIcon = findViewById(R.id.left_icon);
        mTitle = findViewById(R.id.title);
        mRightIcon = findViewById(R.id.right_icon);
        mIv = findViewById(R.id.iv);
        mTvStrat = findViewById(R.id.tv_strat);
        mTvEnd = findViewById(R.id.tv_end);
        mTvPrice = findViewById(R.id.tv_price);
        mTvKm = findViewById(R.id.tv_km);
        mBtn1 = findViewById(R.id.btn1);
        mBtn2 = findViewById(R.id.btn2);
        mTitle.setText("定制班车");
        mLeftIcon.setImageResource(R.drawable.ic_baseline_chevron_left_24);
    }

    @Override
    protected void initData() {
        selectDta(id);
      setimg();
    }

    private void setimg() {
        OkhttpUtil okhttpUtil=new OkhttpUtil();
        okhttpUtil.setGet(User.URI+"/prod-api/api/metro/city","");
        okhttpUtil.setOkhttplim(new Okhttplim() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }

            @Override
            public void getString(String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       ImgBean imgBean=new Gson().fromJson(s,ImgBean.class);
                        String imgUrl = imgBean.getData().getImgUrl();
                        Glide.with(context).load(User.URI+imgUrl)
                                                                  .into(mIv);
                    }
                });
            }
        });
    }

    private void selectDta(String id) {
        OkhttpUtil okhttpUtil=new OkhttpUtil();
        okhttpUtil.setGet(User.URI+"/prod-api/api/bus/line/"+id,"");
        okhttpUtil.setOkhttplim(new Okhttplim() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }

            @Override
            public void getString(String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DTxqBean dTxqBean=new Gson().fromJson(s,DTxqBean.class);
                        DTxqBean.DataDTO data = dTxqBean.getData();
                        mTvEnd.setText(data.getEnd());
                        mTvStrat.setText(data.getFirst());
                        mTvKm.setText(data.getMileage()+"km");
                        mTvPrice.setText("票价:￥"+data.getPrice());
                        price= String.valueOf(data.getPrice());
                        path=data.getName();
                    }
                });
            }
        });
    }

    @Override
    public void setMesg(Mesevent mesg) {
        id= mesg.getDtId();
    }

    @Override
    protected void initEvent() {
   mLeftIcon.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   finish();
               }
           });
   mBtn1.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           finish();
       }
   });
   mBtn2.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           Mesevent mesevent = EventBus.getDefault().removeStickyEvent(Mesevent.class);
           mesevent.setPrice(price);
           mesevent.setPath(path);
           EventBus.getDefault().postSticky(mesevent);
           goActivity(BsdateActivty.class);
       }
   });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dtxq;
    }
}
