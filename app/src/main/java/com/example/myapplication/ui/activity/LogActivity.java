package com.example.myapplication.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.bean.LoginBean;
import com.example.myapplication.bean.User;
import com.example.myapplication.network.OkhttpUtil;
import com.example.myapplication.network.Okhttplim;
import com.example.myapplication.ui.base.BaseActivity;
import com.example.myapplication.util.MesgActivity;
import com.example.myapplication.util.Shape;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class LogActivity extends BaseActivity {


    private android.widget.ImageView mLeftIcon;
    private TextView mTitle;
    private android.widget.ImageView mRightIcon;
    private EditText mUsername;
    private EditText mPsd;
    private TextView mZhuce;
    private Button mBtn;

    @Override
    protected void initView() {


        mLeftIcon = findViewById(R.id.left_icon);
        mTitle = findViewById(R.id.title);
        mRightIcon = findViewById(R.id.right_icon);
        mUsername = findViewById(R.id.username);
        mPsd = findViewById(R.id.psd);
        mZhuce = findViewById(R.id.zhuce);
        mBtn = findViewById(R.id.btn);
        mTitle.setText("登录");
        mLeftIcon.setImageResource(R.drawable.ic_baseline_chevron_left_24);
    }

    @Override
    protected void initData() {
        mUsername.setText(Shape.getValue("usr",""));
        mPsd.setText(Shape.getValue("psd",""));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MesgActivity.exit();
    }

    @Override
    protected void initEvent() {
        //{
        //"username":"test01",
        //"password":"123456"
        //}
        mLeftIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MesgActivity.exit();
            }
        });
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLog(mUsername.getText().toString(),mPsd.getText().toString());
            }
        });
        mZhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goActivity(ZhuceActivity.class);
            }
        });
    }

    private void setLog(String username, String psd) {
        OkhttpUtil okhttpUtil=new OkhttpUtil();
        okhttpUtil.setPost(User.URI+"/prod-api/api/login","","username",username,"password",psd);
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
                LoginBean loginBean=new Gson().fromJson(s,LoginBean.class);
                int code = loginBean.getCode();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (code==200){
                            Toast.makeText(LogActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            Shape.setValue("token",loginBean.getToken());
                            Shape.setValue("usr",mUsername.getText().toString());
                            Shape.setValue("psd",mPsd.getText().toString());
                            goActivity(MainActivity.class);
                        }else {
                            Toast.makeText(LogActivity.this, loginBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return
                R.layout.activity_login;
    }
}
