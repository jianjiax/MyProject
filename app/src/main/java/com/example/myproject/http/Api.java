package com.example.myproject.http;

import com.android.volley.Response;

/**
 * Created by Kain on 2016/8/30.
 */
public interface Api<T> {

    /**
     *
     * @param username
     * @param pw
     * @param listener volley 回调
     */
    void login(String username,String pw,Response.Listener<T> listener);

    /**
     *
     * @param username
     * @param callBack 自定义回调
     */
    void register(String username, CallBack<T> callBack);
}
