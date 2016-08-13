package com.example.myproject.callback;

/**
 * Created by Kain on 2016/8/13.
 */
public class CustomCallBackManager {

    private TestCallBack callBack;

    public CustomCallBackManager() {
    }

    private static class Nest {
        static CustomCallBackManager instance = new CustomCallBackManager();
    }

    public static CustomCallBackManager getInstance() {
        return Nest.instance;
    }

    public void setCallBack(TestCallBack callBack) {
        this.callBack = callBack;
    }

    public TestCallBack getCallBack() {
        return callBack;
    }
}
