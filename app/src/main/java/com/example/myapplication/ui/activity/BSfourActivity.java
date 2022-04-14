package com.example.myapplication.ui.activity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.bean.BsDdBean;
import com.example.myapplication.bean.Mesevent;
import com.example.myapplication.bean.User;
import com.example.myapplication.network.OkhttpUtil;
import com.example.myapplication.network.Okhttplim;
import com.example.myapplication.ui.base.BaseActivity;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class BSfourActivity extends BaseActivity {

    private android.widget.EditText mEtName;
    private android.widget.EditText mEtPhone;
    private android.widget.EditText mEtStart;
    private android.widget.EditText mEtEnd;
    private android.widget.TextView mTvDate;
    private android.widget.Button mBtn1;
    private android.widget.Button mBtn2;
    private String start,end,phone,name,date,price,path;
    private final String TAG="BSfourActivity";
    @Override
    protected void initView() {

        mEtName = findViewById(R.id.et_name);
        mEtPhone = findViewById(R.id.et_phone);
        mEtStart = findViewById(R.id.et_start);
        mEtEnd = findViewById(R.id.et_end);
        mTvDate = findViewById(R.id.tv_date);
        mBtn1 = findViewById(R.id.btn1);
        mBtn2 = findViewById(R.id.btn2);
    }
    @Override
    public void setMesg(Mesevent mesg) {
        name = mesg.getName();
        date = mesg.getDate();
        phone = mesg.getPhone();
        end = mesg.getEnd();
        start = mesg.getStart();
        price=mesg.getPrice();
        path=mesg.getPath();
        Log.d(TAG,price);
    }

    @Override
    protected void initData() {
        mEtName.setText(name);
        mEtPhone.setText(phone);
        mEtStart.setText(start);
        mEtEnd.setText(end);
        mTvDate.setText(date);
    }
    @Override
    protected void initEvent() {
    mBtn1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    });
    mBtn2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            sumit(mEtEnd.getText().toString(),mEtStart.getText().toString()
            ,mEtPhone.getText().toString(),mEtName.getText().toString(),mTvDate.getText().toString());
        }
    });
    }

    private void sumit(String end, String start, String phone, String name, String date) {
        //{
        //"start":"泰德大厦",
        //"end":"大连北站",
        //"price":"8",
        //"path":"一号线",
        //"status":1
        //}
          OkhttpUtil okhttpUtil=new OkhttpUtil();
                  okhttpUtil.setPost(User.URI+"/prod-api/api/bus/order",token,
                          "start",start,"end",end,"price",price,"path",path
                  ,"status","1");
                  okhttpUtil.setOkhttplim(new Okhttplim() {
                      @Override
                      public void onFailure(Call call, IOException e) {
                          runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  Toast.makeText(context, "请求失败", Toast.LENGTH_SHORT).show();
                              }
                          });
                      }

                      @Override
                      public void onResponse(Call call, Response response) throws IOException {

                      }

                      @Override
                      public void getString(String s) {
                         BsDdBean bean=new Gson().fromJson(s,BsDdBean.class);
                          int code = bean.getCode();
                          runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  if (code==200){
                                      Toast.makeText(context, "预定成功成功;订单号"+bean.getOrderNum(), Toast.LENGTH_SHORT).show();
                                  }else {
                                      Toast.makeText(context, bean.getMsg(), Toast.LENGTH_SHORT).show();
                                  }
                              }
                          });
                      }
                  });



    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bsfour;
    }
}
