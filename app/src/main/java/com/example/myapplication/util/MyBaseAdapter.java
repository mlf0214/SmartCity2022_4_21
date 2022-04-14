package com.example.myapplication.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import java.util.List;

public abstract class MyBaseAdapter<T> extends BaseAdapter{
    private int size,layoutId;
    private List<T> list;

    public MyBaseAdapter( List<T> list,int layoutId, int size) {
        this.size = size;
        this.layoutId = layoutId;
        this.list = list;
    }

    @Override
    public int getCount() {
        return size;
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolde viewHolde = getViewHolde(view, viewGroup);
        conerview(viewHolde,list.get(i),i);


        return viewHolde.getContentView();
    }

    protected abstract void conerview(ViewHolde viewHolde, T t, int i);


    private ViewHolde getViewHolde(View view,ViewGroup viewGroup){
        return ViewHolde.getViewHolde(view,viewGroup,layoutId);

    }
}
