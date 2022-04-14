package com.example.myapplication.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.ui.base.BaseActivity;

public class SspActiivty extends BaseActivity {
    private android.widget.RelativeLayout mToolbar;
    private android.widget.ImageView mLeftIcon;
    private android.widget.TextView mTitle;
    private android.widget.ImageView mRightIcon;
    private android.widget.Button mBtn;
    private android.widget.EditText mEtTitle;
    private android.widget.EditText mEtContent;
    private android.widget.ImageView mIv;

    @Override
    protected void initView() {

        mToolbar = findViewById(R.id.toolbar);
        mLeftIcon = findViewById(R.id.left_icon);
        mTitle = findViewById(R.id.title);
        mRightIcon = findViewById(R.id.right_icon);
        mBtn = findViewById(R.id.btn);
        mTitle.setText("随手拍");
        mLeftIcon.setImageResource(R.drawable.ic_baseline_chevron_left_24);
        mEtTitle = findViewById(R.id.et_title);
        mEtContent = findViewById(R.id.et_content);
        mIv = findViewById(R.id.iv);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==200&&resultCode==RESULT_OK){
            Bundle bundle = data.getExtras();
            bitmap= (Bitmap) bundle.get("data");
            Glide.with(context).load(bitmap).into(mIv);
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
   mBtn.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           sumit(mEtTitle.getText().toString(),mEtContent.getText().toString());
       }
   });
   mIv.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
            startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),200);
       }
   });
    }
private Bitmap bitmap=null;
    private void sumit(String title, String content) {
        if (title.equals("")||content.equals("")){
            Toast.makeText(context, "请输入完整信息", Toast.LENGTH_SHORT).show();
        }else {
            if (bitmap==null){
                Toast.makeText(context, "请插入图片", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "反馈成功", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ssp;
    }
}
