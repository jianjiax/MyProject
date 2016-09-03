package com.example.myproject.model;

/**
 * Created by wzg on 15/4/20.
 * 所有对象的基类
 */
public class BaseModel<T> {
    private String code;
    private String msg;
    private String hasnext;
    private T data;

    public BaseModel(String code,String msg,String hasnext,T data) {
        this.code = code;
        this.msg = msg;
        this.hasnext = hasnext;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getHasnext() {
        return hasnext;
    }

    public void setHasnext(String hasnext) {
        this.hasnext = hasnext;
    }

}
