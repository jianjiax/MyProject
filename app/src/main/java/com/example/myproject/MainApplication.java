package com.example.myproject;

import android.app.Application;

/**
 * Created by Kain on 2016/8/13.
 */
public class MainApplication extends Application {


    private static MainApplication mInstance;

    public static MainApplication getInstance() {
        if(mInstance == null){
            mInstance = new MainApplication();
        }
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
