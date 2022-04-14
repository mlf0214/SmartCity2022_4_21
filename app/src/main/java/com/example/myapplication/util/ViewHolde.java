package com.example.myapplication.util;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ViewHolde {
    private View contentView;
    private SparseArray<View> viewSparseArray;
    public ViewHolde( ViewGroup viewGroup,int lYoutId) {
        contentView= LayoutInflater.from(MyApplication.getContext())
                .inflate(lYoutId,viewGroup,false);
        contentView.setTag(this);
        viewSparseArray=new SparseArray<>();
    }
    public static ViewHolde getViewHolde(View view,ViewGroup viewGroup,int layouId){
        if (view==null){
            return new ViewHolde(viewGroup,layouId);
        }else {
            return (ViewHolde) view.getTag();
        }


    }
    public <T extends View> T getView(int viewId){
        View view = viewSparseArray.get(viewId);
        if (view==null){
            view=contentView.findViewById(viewId);
            viewSparseArray.put(viewId,view);
        }
        return (T) view;


    }
    public View getContentView(){
        return contentView;
    }
}
