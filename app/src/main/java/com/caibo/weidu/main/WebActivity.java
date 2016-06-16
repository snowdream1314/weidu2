package com.caibo.weidu.main;

import android.os.Bundle;
import android.webkit.WebView;

import com.caibo.weidu.R;
import com.caibo.weidu.base.TitleLayoutActivity;

public class WebActivity extends TitleLayoutActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        setTitleLayoutTitle(null, getIntent().getStringExtra("title"));
        showBackButton(null);

        webView = (WebView) findViewById(R.id.wv_webview);
        webView.loadUrl(getIntent().getStringExtra("url"));
    }
}
