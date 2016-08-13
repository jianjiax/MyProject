package com.example.myproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myproject.R;
import com.example.myproject.callback.CustomCallBackManager;
import com.example.myproject.callback.TestCallBack;

/**
 * Created by Kain on 2016/8/13.
 */
public class TestBActivity extends BaseActivity implements TestCallBack{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testa);

        CustomCallBackManager.getInstance().setCallBack(this);
        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 实现类似于onActivityResult的功能
                startActivity(new Intent(TestBActivity.this,TestAActivity.class));
            }
        });
    }


    @Override
    public void success(String s) {
        Log.e("successsuccesss == ",s);
    }

    @Override
    public void fail(String s) {

    }
}
