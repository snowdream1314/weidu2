package com.caibo.weidu.main.account.subscribe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.caibo.weidu.R;
import com.caibo.weidu.base.TitleLayoutActivity;

public class AccountSubscribeActivity extends TitleLayoutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_subscribe);

        setTitleLayoutTitle(null, "订阅");
        showBackButton(null);
    }
}
