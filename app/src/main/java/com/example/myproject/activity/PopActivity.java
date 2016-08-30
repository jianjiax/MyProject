package com.example.myproject.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.myproject.R;
import com.example.myproject.widget.ExampleCardPopup;
import com.example.myproject.widget.RelativePopupWindow;

/**
 * Created by Kain on 2016/8/29.
 */
public class PopActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);

        final Spinner spinner_vertical = (Spinner) findViewById(R.id.spinner_vertical);
        ArrayAdapter<String> adapterVertical = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapterVertical.addAll(getResources().getStringArray(R.array.vertical_positions));
        adapterVertical.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_vertical.setAdapter(adapterVertical);

        final Spinner spinner_horizontal = (Spinner) findViewById(R.id.spinner_horizontal);
        ArrayAdapter<String> adapterHorizonal = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapterHorizonal.addAll(getResources().getStringArray(R.array.horizontal_positions));
        adapterHorizonal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_horizontal.setAdapter(adapterHorizonal);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int vertPos;
                switch (spinner_vertical.getSelectedItemPosition()) {
                    case 0:
                        vertPos = RelativePopupWindow.VerticalPosition.ABOVE;
                        break;
                    case 1:
                        vertPos = RelativePopupWindow.VerticalPosition.ALIGN_BOTTOM;
                        break;
                    case 2:
                        vertPos = RelativePopupWindow.VerticalPosition.CENTER;
                        break;
                    case 3:
                        vertPos = RelativePopupWindow.VerticalPosition.ALIGN_TOP;
                        break;
                    default:
                        vertPos = RelativePopupWindow.VerticalPosition.BELOW;
                        break;
                }

                final int horizPos;
                switch (spinner_horizontal.getSelectedItemPosition()) {
                    case 0:
                        horizPos = RelativePopupWindow.HorizontalPosition.LEFT;
                        break;
                    case 1:
                        horizPos = RelativePopupWindow.HorizontalPosition.ALIGN_RIGHT;
                        break;
                    case 2:
                        horizPos = RelativePopupWindow.HorizontalPosition.CENTER;
                        break;
                    case 3:
                        horizPos = RelativePopupWindow.HorizontalPosition.ALIGN_LEFT;
                        break;
                    default:
                        horizPos = RelativePopupWindow.HorizontalPosition.RIGHT;
                        break;
                }

                new ExampleCardPopup(view.getContext()).showOnAnchor(view, vertPos, horizPos);
            }
        });
    }


    @Override
    public void onCreateCustomToolBar(Toolbar toolbar) {
        super.onCreateCustomToolBar(toolbar);
        toolbar.showOverflowMenu() ;
//        getLayoutInflater().inflate(R.layout.toobar_button, toolbar) ;


        Button btn = (Button) toolbar.findViewById(R.id.bt1);

    }
}
