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

public class YjkfActivity extends BaseActivity {
    private android.widget.ImageView mLeftIcon;
    private android.widget.TextView mTitle;
    private android.widget.ImageView mRightIcon;
    private android.widget.EditText mEt;
    private android.widget.Button mBtn;

    @Override
    protected void initView() {

        mLeftIcon = findViewById(R.id.left_icon);
        mTitle = findViewById(R.id.title);
        mRightIcon = findViewById(R.id.right_icon);
        mEt = findViewById(R.id.et);
        mTitle.setText("意见反馈");
        mLeftIcon.setImageResource(R.drawable.ic_baseline_chevron_left_24);
        mBtn = findViewById(R.id.btn);
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
           sumit(mEt.getText().toString());
       }
   });
    }

    private void sumit(String conten) {
        //"content": "反馈内容",
          OkhttpUtil okhttpUtil=new OkhttpUtil();
                  okhttpUtil.setPost(User.URI+"/prod-api/api/common/feedback",token,"content",conten);
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
                                      Toast.makeText(context, "发表成功", Toast.LENGTH_SHORT).show();
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
        return R.layout.activity_yjfk;
    }
}
