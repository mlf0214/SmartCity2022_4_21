package com.example.myapplication.ui.activity;

import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.bean.UseeSetBaern;
import com.example.myapplication.bean.User;
import com.example.myapplication.bean.UserBean;
import com.example.myapplication.network.OkhttpUtil;
import com.example.myapplication.network.Okhttplim;
import com.example.myapplication.ui.base.BaseActivity;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class UserSetActivity extends BaseActivity {
    private android.widget.ImageView mLeftIcon;
    private android.widget.TextView mTitle;
    private android.widget.ImageView mRightIcon;
    private android.widget.ImageView mIv;
    private android.widget.EditText mEtNickname;
    private android.widget.EditText mEtPhone;
    private android.widget.RadioButton mRbNan;
    private android.widget.RadioButton mRbNv;
    private android.widget.Button mBtn;

    @Override
    protected void initView() {

        mLeftIcon = findViewById(R.id.left_icon);
        mTitle = findViewById(R.id.title);
        mRightIcon = findViewById(R.id.right_icon);
        mIv = findViewById(R.id.iv);
        mEtNickname = findViewById(R.id.et_nickname);
        mEtPhone = findViewById(R.id.et_phone);
        mRbNan = findViewById(R.id.rb_nan);
        mRbNv = findViewById(R.id.rb_nv);
        mBtn = findViewById(R.id.btn);
          mTitle.setText("个人信息");
                  mLeftIcon.setImageResource(R.drawable.ic_baseline_chevron_left_24);
    }

    @Override
    protected void initData() {
        //.点击个人信息跳转至个人信息页面，标签栏显示本页面标题，
        // 点击返回图标可返回到上一页，点击修改可保存修改的信息，
        // 可修改内容为：头像、昵称、性别、联系电话，注：手机号码后四位使用*号代替。
        selectUseData();
        Glide.with(getApplicationContext()).load(R.drawable.ic_launcher_foreground)
                                                  .into(mIv);
    }

    private void selectUseData() {
        OkhttpUtil okhttpUtil=new OkhttpUtil();
        okhttpUtil.setGet(User.URI+"/prod-api/api/common/user/getInfo",token);
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
                        UserBean userBean=new Gson().fromJson(s,UserBean.class);
                        UserBean.UserDTO user = userBean.getUser();
                        mEtNickname.setText(user.getNickName());
                        String phonenumber = user.getPhonenumber();
                        String s1 = phonenumber.substring(0, phonenumber.length() - 4) + "****";
                        mEtPhone.setText(s1);
                        String sex = user.getSex();
                        if (sex.equals("0")){
                            mRbNan.setChecked(true);
                        }else {
                            mRbNv.setChecked(true);
                        }
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
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sex;
                if (mRbNan.isChecked()){
                    sex="0";
                }else {
                    sex="1";
                }
                sumit(mEtNickname.getText().toString(),mEtPhone.getText().toString(),sex);

            }
        });
    }

    private void sumit(String nickname, String phone, String sex) {
          OkhttpUtil okhttpUtil=new OkhttpUtil();
          //{
        //"email": "lixl@163.com",
        //"idCard": "210882199807251656",
        //"nickName": "大卫王",
        //"phonenumber": "15898125461",
        //"sex": "0"
        //}
                  okhttpUtil.setPut(User.URI+"/prod-api/api/common/user",token,"nickName",nickname
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
                          runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  int code = useeSetBaern.getCode();
                                  if (code==200){
                                      Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
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
        return R.layout.activity_userset;
    }
}
