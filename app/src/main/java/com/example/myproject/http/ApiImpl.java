package com.example.myproject.http;

import android.app.Activity;

import com.android.volley.Response;
import com.example.myproject.activity.BaseActivity;
import com.example.myproject.model.BaseModel;
import com.example.myproject.model.TestModel;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by Kain on 2016/8/30.
 */
public final class ApiImpl implements Api {

    private volatile static ApiImpl sInstance;

    private ApiImpl mApi;
    private static Activity activity;

//    private ApiImpl() {
//        mApi = new ApiImpl();
//    }

    public static Api getInstance(Activity paramActivity) {
        if (null == sInstance)
            synchronized (Api.class) {
                sInstance = new ApiImpl();
            }
        activity = paramActivity;
        return sInstance;
    }

    @Override
    public void login(String username, String pw, Response.Listener listener) {
        String url = "http://kuaidian.ree9.com/MerchantApi/Public/checkUpdate?plat=android&token=552a4b33147416e9462d2fec&ver=1.3.1&sig=ciD%2B5FtwQv99aKutUbay5pHXT%2FPQ4cvfYjKxnJmvQl%2BVTrnosuSZbju%2Fktw%3D";
        Type type = new TypeToken<BaseModel<TestModel>>() {
        }.getType();
        GsonRequest request = new GsonRequest(url, type,listener);
        if(activity != null){
            ((BaseActivity)activity).addToRequestQueue(request);
        }
    }

    @Override
    public void register(String username, final CallBack callBack) {
        String url = "http://kuaidian.ree9.com/MerchantApi/Public/checkUpdate?plat=android&token=552a4b33147416e9462d2fec&ver=1.3.1&sig=ciD%2B5FtwQv99aKutUbay5pHXT%2FPQ4cvfYjKxnJmvQl%2BVTrnosuSZbju%2Fktw%3D";
        Type type = new TypeToken<BaseModel<TestModel>>() {
        }.getType();
        GsonRequest request = new GsonRequest(url, type, new Response.Listener<BaseModel<TestModel>>() {
            @Override
            public void onResponse(BaseModel<TestModel> response) {
                callBack.onFinish(true,new BaseModel("","","",response.getData()),"");
            }
        });
        if(activity != null){
            ((BaseActivity)activity).addToRequestQueue(request);
        }
    }
}
