package com.caibo.weidu.main.account;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caibo.weidu.R;
import com.caibo.weidu.base.TitleLayoutFragment;
import com.caibo.weidu.util.WDRequest;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends TitleLayoutFragment implements WDRequest.WDRequestDelegate{

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
        if ( rootView == null ) {
            rootView = inflater.inflate(R.layout.fragment_account, container, false);

            View view = rootView;
            setTitleLayoutTitle(view, "公众号");


        }

        return rootView;
    }

    @Override
    public void requestSuccess(WDRequest req, String result) {

    }

    @Override
    public void requestFail(WDRequest req, String result) {

    }
}
