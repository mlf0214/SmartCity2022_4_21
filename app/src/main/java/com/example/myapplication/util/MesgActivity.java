package com.example.myapplication.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class MesgActivity {
    public static List<Activity> activities=new ArrayList<>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }
    public static void exit(){
        for (Activity a:
             activities) {
            a.finish();
        }
    }

}
