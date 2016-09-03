package com.example.myproject.http;

/**
 * Created by Kain on 2015/12/4.
 */

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.myproject.model.TestModel;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Volley adapter for JSON requests that will be parsed into Java objects by Gson.
 */
public class GsonRequest<T> extends Request<T> {
    private Gson mGson;
    private Class<T> clazz;
    private Map<String, String> headers;
    private Map<String, String> params;
    private Response.Listener<T> listener;
    private Type type;
    private String url;

    T type1;

//    private static HttpDNS httpdnsService = HttpDNS.getInstance();


    /**
     * Make a GET request and return a parsed object from JSON.
     * without errorListener
     * @param url   URL of the request to make
     * @param type Relevant class object, for Gson's reflection
     */
    public GsonRequest(String url,
                       Type type,
                       Response.Listener<T> listener) {

        super(Method.GET, url, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String s;
                if (TextUtils.isEmpty(error.getMessage())
                        || error.getCause() instanceof UnknownHostException
                        || error.getCause() instanceof RuntimeException
                        || error.getCause() instanceof ConnectException) {
                    s = "网络异常";
                } else if(error.getCause() instanceof JsonSyntaxException){
                    s = "暂无数据";
                }else{
                    s = error.getMessage().replace("com.youngt.kuaidian.exception.CustomException:", "");
                }
                // 如“暂无数据”不需要toast，可在此判断
//                Toast.makeText(QsdApplication.getContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
//        this.clazz = clazz;
        this.listener = listener;
        this.url = url;
        this.type = type;
        mGson = new Gson();
    }

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url   URL of the request to make
     * @param type Relevant class object, for Gson's reflection
     * @param listener for success listener
     * @param errorListener for error listener
     */
    public GsonRequest(String url,
                       Type type,
                       Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {

        super(Method.GET, url, errorListener);

        this.listener = listener;
        this.url = url;
        this.type = type;
        mGson = new Gson();
    }

    /**
     * Make a POST request and return a parsed object from JSON.
     * without errorListener
     * @param url   URL of the request to make
     * @param type Relevant class object, for Gson's reflection （or Class）
     */
    public GsonRequest(String url,
                       Class<T> clazz,
                       Type type,
                       Map<String, String> params,
                       Response.Listener<T> listener) {

        super(Method.POST, url, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String s;
                if (TextUtils.isEmpty(error.getMessage())
                        || error.getCause() instanceof UnknownHostException
                        || error.getCause() instanceof RuntimeException
                        || error.getCause() instanceof ConnectException) {
                    s = "网络异常";
                } else if(error.getCause() instanceof JsonSyntaxException){
                    s = "暂无数据";
                }else{
                    s = error.getMessage().replace("com.youngt.kuaidian.exception.CustomException:", "");
                }
//                Toast.makeText(QsdApplication.getContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
//        this.clazz = clazz;
        this.type = type;
        this.params = params;
        this.url = url;
        this.listener = listener;
        this.headers = null;
        mGson = new Gson();
    }

    /**
     * Make a POST request and return a parsed object from JSON.
     *
     * @param url   URL of the request to make
     * @param type Relevant class object, for Gson's reflection （or Class）
     * @param listener for success listener
     * @param errorListener for error listener
     */
    public GsonRequest(String url,
//                       Class<T> clazz,
                       Type type,
                       Map<String, String> params,
                       Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {

        super(Method.POST, url, errorListener);
//        this.clazz = clazz;
        this.type = type;
        this.params = params;
        this.url = url;
        this.listener = listener;
        this.headers = null;
        mGson = new Gson();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers1 = new HashMap<String, String>();
//        headers1.put("Charset", "test encoding");
//        headers1.put("Content-Type", "application/x-javascript");
//        headers1.put("User-Agent", "android-open-project-analysis/1.0");
//        headers1.put("Accept-Encoding", "test encoding");

//        if(!url.startsWith("http://neighborapi")){
//            headers1.put("Host", "kuaidian.youngt.net");
//        }
        return headers1;
//        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {

        return params;
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    private boolean isSuccess = false;

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String msg = "网络异常";
        String code = "0";
        try {
            Log.e("GsonRequest", new String(response.data));
            String jsonStr = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            JSONObject jsonObject = new JSONObject(jsonStr);
            code = jsonObject.getString("code");
            msg = jsonObject.getString("msg");
            Log.e("gsonurl", url + "+++code = " + code);
            if (code.equals("0")) {
                isSuccess = true;
                return (Response<T>) Response.success(mGson.fromJson(jsonStr, type),
                        HttpHeaderParser.parseCacheHeaders(response));
            } else {
                return Response.error(new ParseError(new CustomException(msg)));
            }
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(new CustomException(msg)));
        } catch (JsonSyntaxException e) {
            e.printStackTrace();

            if(isSuccess){
                return Response.error(new ParseError(new CustomException("暂无数据")));
            }else{
                return Response.error(new ParseError(new CustomException("解析异常")));
            }
//            return Response.success(mGson.fromJson("{\"code\":0,\"msg\":\"ok\",\"hasnext\":0,\"data\":{}}", type),
//                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (JSONException e) {
            e.printStackTrace();
            return Response.error(new ParseError(new CustomException("解析异常")));
        }
    }
}