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

public class PsdSetActivity extends BaseActivity {
    private android.widget.ImageView mLeftIcon;
    private android.widget.TextView mTitle;
    private android.widget.ImageView mRightIcon;

    private android.widget.Button mBtn;
    private android.widget.EditText mOldpsd;
    private android.widget.EditText mNewpsd;


    @Override
    protected void initView() {

        mLeftIcon = findViewById(R.id.left_icon);
        mTitle = findViewById(R.id.title);
        mRightIcon = findViewById(R.id.right_icon);
        mTitle.setText("修改密码");
        mLeftIcon.setImageResource(R.drawable.ic_baseline_chevron_left_24);
        mBtn = findViewById(R.id.btn);

        mOldpsd = findViewById(R.id.oldpsd);
        mNewpsd = findViewById(R.id.newpsd);
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
   mBtn.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           sumit(mOldpsd.getText().toString(),mNewpsd.getText().toString());
       }
   });
    }

    private void sumit(String oldpsd, String newold) {
          OkhttpUtil okhttpUtil=new OkhttpUtil();
          //{
        //"newPassword": "123789",
        //"oldPassword": "123456"
        //}
                  okhttpUtil.setPut(User.URI+"/prod-api/api/common/user/resetPwd",token,"newPassword",newold,"oldPassword",oldpsd);
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

                          runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  int code = useeSetBaern.getCode();
                                  if (code==200){
                                      Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
                                  }
                                  else {
                                      Toast.makeText(context, useeSetBaern.getMsg(), Toast.LENGTH_SHORT).show();
                                  }
                              }
                          });

                      }
                  });

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_psdset;
    }
}
