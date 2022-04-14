package com.example.myapplication.ui.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.bean.Mesevent;
import com.example.myapplication.bean.User;
import com.example.myapplication.bean.ZhBsListBean;
import com.example.myapplication.network.OkhttpUtil;
import com.example.myapplication.network.Okhttplim;
import com.example.myapplication.ui.base.BaseActivity;
import com.example.myapplication.util.MyBaseAdapter;
import com.example.myapplication.util.ViewHolde;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class ZHBSACtivity extends BaseActivity {
    private android.widget.RelativeLayout mToolbar;
    private android.widget.ImageView mLeftIcon;
    private android.widget.TextView mTitle;
    private android.widget.ImageView mRightIcon;
    private com.example.myapplication.util.MyListView mMlv;

    @Override
    protected void initView() {
        //（1）页面显示巴士列表，列表显示路线名称、

        mToolbar = findViewById(R.id.toolbar);
        mLeftIcon = findViewById(R.id.left_icon);
        mTitle = findViewById(R.id.title);
        mRightIcon = findViewById(R.id.right_icon);
        mMlv = findViewById(R.id.mlv);
        mTitle.setText("智慧巴士");
        mLeftIcon.setImageResource(R.drawable.ic_baseline_chevron_left_24);
    }

    @Override
    protected void initData() {
        selectMlvDta();
    }

    private void selectMlvDta() {
        OkhttpUtil okhttpUtil = new OkhttpUtil();
        okhttpUtil.setGet(User.URI + "/prod-api/api/bus/line/list", "");
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
                        // 起终点、运行时间、票价、以及里程、巴士列表具有扩展显示该路线的各个站点功能。
                        ZhBsListBean zhBsListBean = new Gson().fromJson(s, ZhBsListBean.class);
                        List<ZhBsListBean.RowsDTO> rows = zhBsListBean.getRows();
                        mMlv.setAdapter(new MyBaseAdapter<ZhBsListBean.RowsDTO>(rows, R.layout.zhbslist, rows.size()) {
                            private TextView mTvDate;
                            private TextView mTvKm;
                            private TextView mTvPrice;
                            private TextView mTvEnd;
                            private TextView mTvStart;
                            @Override
                            protected void conerview(ViewHolde viewHolde, ZhBsListBean.RowsDTO rowsDTO, int i) {
                                mTvStart =  viewHolde.getView(R.id.tv_start);
                                mTvEnd =    viewHolde.getView(R.id.tv_end);
                                mTvPrice =  viewHolde.getView(R.id.tv_price);
                                mTvKm =     viewHolde.getView(R.id.tv_km);
                                mTvDate =   viewHolde.getView(R.id.tv_date);
                                mTvStart.setText(rowsDTO.getFirst());
                                mTvEnd.setText(rowsDTO.getEnd());
                                mTvPrice.setText("票价:￥"+rowsDTO.getPrice());
                                mTvDate.setText(rowsDTO.getStartTime()+"至"+rowsDTO.getEndTime());
                                mTvKm.setText(rowsDTO.getMileage()+"公里");
                            }
                        });
                        mMlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                int id = rows.get(i).getId();
                                Mesevent mesevent=new Mesevent();
                                mesevent.setDtId(String.valueOf(id));
                                EventBus.getDefault().postSticky(mesevent);
                                goActivity(BsXqActivity.class);
                            }
                        });
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
        return R.layout.activity_zhbs;
    }
}
