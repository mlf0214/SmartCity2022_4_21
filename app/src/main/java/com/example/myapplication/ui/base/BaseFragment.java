package com.example.myapplication.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
    public Activity mActivity;
    private View view;
    public int x,y;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(getLayoutId(),container,false);
        initView();
        initwindows();
        initData();
        initEvent();
        return view;
    }

    private void initwindows() {
        DisplayMetrics displayMetrics = mActivity.getResources().getDisplayMetrics();
        y = displayMetrics.heightPixels;
         x = displayMetrics.widthPixels;
    }

    public View getView(){
        return this.view;
}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity= (Activity) getContext();
    }

    public void goActivity(Class<?> c){
        startActivity(new Intent(mActivity,c));
    }
    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initEvent();

    protected abstract int getLayoutId();
}
