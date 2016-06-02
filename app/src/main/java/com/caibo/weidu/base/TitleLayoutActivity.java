package com.caibo.weidu.base;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.caibo.weidu.R;

public class TitleLayoutActivity extends Activity implements TitleLayout{

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
}
