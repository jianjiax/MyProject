package com.example.myproject.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.example.myproject.callback.CustomCallBackManager;
import com.example.myproject.helper.ToolBarHelper;

/**
 * Created by Kain on 2016/8/3.
 */
public class BaseActivity extends AppCompatActivity {
    private ToolBarHelper mToolBarHelper;
    public Toolbar toolbar;

    private RequestQueue requestQueue;
    private String REQUESTTAG = "default";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {

        mToolBarHelper = new ToolBarHelper(this, layoutResID);
        toolbar = mToolBarHelper.getToolBar();

        setContentView(mToolBarHelper.getContentView()); /*把 toolbar 设置到Activity 中*/

        setSupportActionBar(toolbar); /*自定义的一些操作*/
        onCreateCustomToolBar(toolbar);
    }

    public void onCreateCustomToolBar(Toolbar toolbar) {
        toolbar.setContentInsetsRelative(0, 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * @return The Volley Request queue, the queue will be created if it is null
     */
    public RequestQueue getRequestQueue(boolean hasPb) {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (requestQueue == null) {
            synchronized (BaseActivity.class) {
                if (requestQueue == null) {
                    requestQueue = Volley
                            .newRequestQueue(getApplicationContext());
                    requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
                        @Override
                        public void onRequestFinished(Request<Object> request) {
//                            if (mRefreshLayout != null) {
//                                Log.e(TAG,"refreshLayoutrefreshLayout");
//                                mRefreshLayout.setLoading(false);
//                                mRefreshLayout.setRefreshing(false);
//                            }
//                            closeProgressDialog();
                        }
                    });
                }
            }
        }else{

        }
        return requestQueue;
    }


    /**
     * Adds the specified request to the global queue, if tag is specified then
     * it is used else Default TAG is used.
     *
     * @param req
     * @param tag
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? REQUESTTAG : tag);

        VolleyLog.d("Adding request to queue: %s", req.getUrl());

        getRequestQueue(true).add(req);
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     *
     * @param req
     */
    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        req.setTag(REQUESTTAG);
        getRequestQueue(true).add(req);
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     *
     * @param req
     */
    public <T> void addToRequestQueue(Request<T> req, boolean hasPb) {
        // set the default tag if tag is empty
        req.setTag(REQUESTTAG);
        getRequestQueue(hasPb).add(req);
    }

    /**
     * Cancels all pending requests by the specified TAG, it is important to
     * specify a TAG so that the pending/ongoing requests can be cancelled.
     *
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }
}