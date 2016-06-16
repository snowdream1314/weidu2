package com.caibo.weidu.base;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageButton;
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

    @Override
    public void showBackButton(View view) {
        showBackButton(view, null);
    }

    private void showBackButton(View view, View.OnClickListener clickListener) {
        ImageButton back = (ImageButton) view.findViewById(R.id.ib_back);
        if (clickListener == null) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
        } else {
            back.setOnClickListener(clickListener);
        }

        back.setVisibility(View.VISIBLE);
    }
}
