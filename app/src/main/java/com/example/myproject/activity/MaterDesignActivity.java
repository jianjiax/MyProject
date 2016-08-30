package com.example.myproject.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myproject.R;

/**
 * Created by Kain on 2016/8/29.
 */
public class MaterDesignActivity extends BaseActivity {

    LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materdesign);

        ll = (LinearLayout)findViewById(R.id.ll);
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
        toolbar.showOverflowMenu() ;
//        getLayoutInflater().inflate(R.layout.toobar_button, toolbar) ;


        Button btn = (Button) toolbar.findViewById(R.id.bt1);

    }

}
