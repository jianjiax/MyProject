package com.example.myproject.activity;

import android.os.Bundle;
import android.view.View;

import com.example.myproject.R;
import com.example.myproject.callback.CustomCallBackManager;

/**
 * Created by Kain on 2016/8/13.
 */
public class TestAActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testa);
        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomCallBackManager.getInstance().getCallBack().success("axiba");
            }
        });
    }
}
