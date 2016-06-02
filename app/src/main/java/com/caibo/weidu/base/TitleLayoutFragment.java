package com.caibo.weidu.base;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.caibo.weidu.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TitleLayoutFragment extends Fragment implements TitleLayout{

    @Override
    public void setTitleLayoutTitle(View view, String title) {
        TextView titleView = (TextView) view.findViewById(R.id.tv_title);
        if ( titleView != null ) {
            titleView.setText(title);
        }
    }
}
