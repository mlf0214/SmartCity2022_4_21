package com.example.myapplication.ui.activity;

import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.bean.UseeSetBaern;
import com.example.myapplication.bean.User;
import com.example.myapplication.network.OkhttpUtil;
import com.example.myapplication.network.Okhttplim;
import com.example.myapplication.ui.base.BaseActivity;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class ZhuceActivity extends BaseActivity {
    private android.widget.ImageView mLeftIcon;
    private android.widget.TextView mTitle;
    private android.widget.ImageView mRightIcon;
    private android.widget.EditText mEtNickname;
    private android.widget.EditText mEtPsd;
    private android.widget.EditText mEtPhone;
    private android.widget.RadioButton mRbNan;
    private android.widget.RadioButton mRbNv;
    private android.widget.Button mBtn;

    @Override
    protected void initView() {

        mLeftIcon = findViewById(R.id.left_icon);
        mTitle = findViewById(R.id.title);
        mRightIcon = findViewById(R.id.right_icon);
        mEtNickname = findViewById(R.id.et_nickname);
        mEtPsd = findViewById(R.id.et_psd);
        mEtPhone = findViewById(R.id.et_phone);
        mRbNan = findViewById(R.id.rb_nan);
        mRbNv = findViewById(R.id.rb_nv);
        mBtn = findViewById(R.id.btn);
        mTitle.setText("注册");
        mLeftIcon.setImageResource(R.drawable.ic_baseline_chevron_left_24);
        mRbNan.setChecked(true);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
           mLeftIcon.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           finish();
                       }
                   });
        //{
        //"avatar": "/profile/2020/10/26/27e7fd58-0972-4dbf-941c-590624e6a886.
        //png",
        //"userName": "David",
        //"nickName": "大卫",
        //"password": "123456",
        //"phonenumber": "15840669812",
        //"sex": "0",
        //"email": "David@163.com",
        //"idCard": "210113199808242137"
        //}
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sex;
                if (mRbNan.isChecked()){
                    sex="0";
                }else {
                    sex="1";
                }
                sumit(mEtNickname.getText().toString(),mEtPhone.getText().toString(),mEtPsd.getText().toString(),sex);
            }
        });
    }

    private void sumit(String username, String phone, String psd, String sex) {
          OkhttpUtil okhttpUtil=new OkhttpUtil();
                  okhttpUtil.setPost(User.URI+"/prod-api/api/register","","userName",username,"password",psd
                  ,"phonenumber",phone,"sex",sex);
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
                          UseeSetBaern useeSetBaern=new Gson().fromJson(s,UseeSetBaern.class);
                          int code = useeSetBaern.getCode();
                          runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  if (code==200){
                                      Toast.makeText(context, "注册成功", Toast.LENGTH_SHORT).show();
                                      goActivity(LogActivity.class);
                                  }else {
                                      Toast.makeText(context, useeSetBaern.getMsg(), Toast.LENGTH_SHORT).show();
                                  }
                              }
                          });
                      }
                  });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zhuce;
    }
}
