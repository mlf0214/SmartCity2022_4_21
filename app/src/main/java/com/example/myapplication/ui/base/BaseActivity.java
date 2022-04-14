package com.example.myapplication.ui.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.myapplication.bean.Mesevent;
import com.example.myapplication.util.MesgActivity;
import com.example.myapplication.util.MyApplication;
import com.example.myapplication.util.Shape;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class BaseActivity extends FragmentActivity {
private String[] quanxinas={"android.permission.CALL_PHONE",
        "android.permission.CAMERA"};
public String token;
public Context context=MyApplication.getContext();
public int x,y;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        EventBus.getDefault().register(this);
        MesgActivity.addActivity(this);
        initBaseData();
        setquanixna();
        initView();
        initwindows();
        initData();
        initEvent();
    }
    private void initwindows() {
        DisplayMetrics displayMetrics =getResources().getDisplayMetrics();
        y = displayMetrics.heightPixels;
        x = displayMetrics.widthPixels;
    }

    public void initBaseData(){
    token= Shape.getValue("token","");
    }
    public void goActivity(Class<?> c){
        startActivity(new Intent(MyApplication.getContext(),c));
    }
    private void setquanixna(){
        for (int i = 0; i < quanxinas.length; i++) {
            if (ContextCompat.checkSelfPermission(this,quanxinas[i])!=
                    PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,quanxinas,200);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean isNo=false;
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==200){
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i]==-1){
                    isNo=true;
                }
            }
            if (isNo){
//                ActivityCompat.requestPermissions(this,quanxinas,200);
                setquanixna();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void setMesg(Mesevent mesg){
    }
    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initEvent();

    protected abstract int getLayoutId();
}
