package com.example.myapplication.ui.activity;

import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.bean.DTxqBean;
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

public class BsThirActivity extends BaseActivity {
    private android.widget.RelativeLayout mToolbar;
    private android.widget.ImageView mLeftIcon;
    private android.widget.TextView mTitle;
    private android.widget.ImageView mRightIcon;
    private android.widget.TextView mTvStrat;
    private android.widget.TextView mTvEnd;
    private android.widget.EditText mEtName;
    private android.widget.EditText mEtPhone;
    private android.widget.EditText mEtStart;
    private android.widget.EditText mEtEnd;
    private android.widget.Button mBtn1;
    private android.widget.Button mBtn2;
    private String id;

    @Override
    protected void initView() {

        mToolbar = findViewById(R.id.toolbar);
        mLeftIcon = findViewById(R.id.left_icon);
        mTitle = findViewById(R.id.title);
        mRightIcon = findViewById(R.id.right_icon);
        mTvStrat = findViewById(R.id.tv_strat);
        mTvEnd = findViewById(R.id.tv_end);
        mEtName = findViewById(R.id.et_name);
        mEtPhone = findViewById(R.id.et_phone);
        mEtStart = findViewById(R.id.et_start);
        mEtEnd = findViewById(R.id.et_end);
        mBtn1 = findViewById(R.id.btn1);
        mBtn2 = findViewById(R.id.btn2);
    }

    @Override
    public void setMesg(Mesevent mesg) {
        id = mesg.getDtId();
    }

    @Override
    protected void initData() {
    selectDta(id);
    selectUser();
    }

    private void selectUser() {

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
                        mEtEnd.setText(data.getEnd());
                        mEtStart.setText(data.getFirst());
                    }
                });
            }
        });
    }

    @Override
    protected void initEvent() {
        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mesevent mesevent = EventBus.getDefault().removeStickyEvent(Mesevent.class);
                mesevent.setName(mEtName.getText().toString());
                mesevent.setPhone(mEtPhone.getText().toString());
                mesevent.setStart(mEtStart.getText().toString());
                mesevent.setEnd(mEtEnd.getText().toString());
                EventBus.getDefault().postSticky(mesevent);
                goActivity(BSfourActivity.class);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bsthir;
    }
}
