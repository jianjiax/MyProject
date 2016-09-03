package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.myproject.activity.BaseActivity;
import com.example.myproject.activity.MaterDesignActivity;
import com.example.myproject.activity.PopActivity;
import com.example.myproject.activity.TestBActivity;
import com.example.myproject.http.ApiImpl;
import com.example.myproject.http.CallBack;
import com.example.myproject.model.BaseModel;
import com.example.myproject.model.TestModel;
import com.example.myproject.utils.LogUtils;
import com.gc.materialdesign.views.ButtonIcon;
import com.gc.materialdesign.views.ButtonRectangle;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

public class MainActivity extends BaseActivity implements ObservableScrollViewCallbacks,
        View.OnClickListener {


    TextView tv1;

    ButtonRectangle pop_bt;
    ButtonIcon materdesign_bt;
    ButtonRectangle request_bt;
    ButtonRectangle request1_bt;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(getApplicationContext(),null);

        pop_bt = (ButtonRectangle) findViewById(R.id.pop_bt);
        request_bt = (ButtonRectangle) findViewById(R.id.request_bt);
        request1_bt = (ButtonRectangle) findViewById(R.id.request1_bt);
        materdesign_bt = (ButtonIcon) findViewById(R.id.materdesign_bt);
        pop_bt.setOnClickListener(this);
        materdesign_bt.setOnClickListener(this);
        request_bt.setOnClickListener(this);
        request1_bt.setOnClickListener(this);

        tv1 = (TextView) findViewById(R.id.tv1);
        ObservableRecyclerView gridView = (ObservableRecyclerView) findViewById(R.id.grid);
        gridView.setScrollViewCallbacks(this);
        gridView.setLayoutManager(new LinearLayoutManager(this));
        gridView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new CusViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                final CusViewHolder cusViewHolder = (CusViewHolder) holder;
                cusViewHolder.tv1.setText("12312");
            }

            @Override
            public int getItemCount() {
                return 10;
            }
        });
    }

    public static class CusViewHolder extends RecyclerView.ViewHolder {
        TextView tv1;

        public CusViewHolder(View paramView) {
            super(paramView);
            this.tv1 = ((TextView) paramView.findViewById(R.id.tv1));
        }
    }

    @Override
    public void onCreateCustomToolBar(Toolbar toolbar) {
        super.onCreateCustomToolBar(toolbar);
        toolbar.showOverflowMenu();
//        getLayoutInflater().inflate(R.layout.toobar_button, toolbar) ;


        Button btn = (Button) toolbar.findViewById(R.id.bt1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestBActivity.class));
            }
        });

    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        if (scrollState == ScrollState.UP) {
            tv1.setVisibility(View.GONE);
        } else if (scrollState == ScrollState.DOWN) {
            tv1.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pop_bt:
                intent = new Intent(MainActivity.this, PopActivity.class);
                startActivity(intent);
                break;
            case R.id.materdesign_bt:
                intent = new Intent(MainActivity.this, MaterDesignActivity.class);
                startActivity(intent);
                break;
            case R.id.request_bt:
                // volley response
                ApiImpl.getInstance(MainActivity.this).login("","",new Response.Listener<BaseModel<TestModel>>() {
                    //
                    @Override
                    public void onResponse(BaseModel<TestModel> response) {
                        Log.e("asdqaeqwwr", response.getData().getDescription());
                    }
                });
                break;
            case R.id.request1_bt:
                // custom response
                ApiImpl.getInstance(MainActivity.this).register("", new CallBack<TestModel>() {
                    @Override
                    public void onFinish(boolean isSuccess, BaseModel<TestModel> response, String error) {

                        LogUtils.e("asdasd == " + response.getData().getDescription());
                    }
                });
                break;
        }
    }
}
