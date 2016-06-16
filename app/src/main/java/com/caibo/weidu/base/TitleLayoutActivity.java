package com.caibo.weidu.base;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.caibo.weidu.R;

public class TitleLayoutActivity extends FragmentActivity implements TitleLayout{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setTitleLayoutTitle(View view, String title) {
        TextView titleView = (TextView) findViewById(R.id.tv_title);
        if ( titleView != null ) {
            titleView.setText(title);
        }
    }

    @Override
    public void showBackButton(View view) {
        showBackButton(view, null);
    }

    private void showBackButton(View view, View.OnClickListener clickListener) {
        ImageButton back = (ImageButton) findViewById(R.id.ib_back);
        if (clickListener == null) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            back.setOnClickListener(clickListener);
        }

        back.setVisibility(View.VISIBLE);
    }
}
