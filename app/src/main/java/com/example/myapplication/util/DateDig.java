package com.example.myapplication.util;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class DateDig {
    public static void showTimeDig(Context context, TextView textView){
        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                textView.setText(i+":"+i1);
            }
        },12,13,true).show();
    }
    public static void showDateDig(Context context,TextView textView){
        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String n ,y,r;
                n= String.valueOf(i);
                r= String.valueOf(i2);
                if (i1<10){
                    y="0"+(i1+1);
                }else {
                    y= String.valueOf(i1+1);
                }
                textView.setText(n+"-"+y+"-"+r);
            }
        },2020,02,14).show();
    }


}
