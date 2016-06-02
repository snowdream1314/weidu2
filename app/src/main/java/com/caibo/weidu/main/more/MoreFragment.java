package com.caibo.weidu.main.more;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caibo.weidu.R;
import com.caibo.weidu.base.TitleLayoutFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends TitleLayoutFragment{

    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if( rootView == null ) {
            rootView = inflater.inflate(R.layout.fragment_more, container, false);

            View view = rootView;
            setTitleLayoutTitle(view, "更多");
        }

        return rootView;
    }


}
