package com.caibo.weidu.main.account.accountcategory;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.caibo.weidu.R;
import com.caibo.weidu.base.TitleLayoutActivity;

public class AccountCatActivity extends TitleLayoutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_cat);

        setTitleLayoutTitle(null, getIntent().getStringExtra("category_name"));
        showBackButton(null);
    }
}
