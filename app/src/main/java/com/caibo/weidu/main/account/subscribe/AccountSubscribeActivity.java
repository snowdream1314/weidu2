package com.caibo.weidu.main.account.subscribe;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.caibo.weidu.R;
import com.caibo.weidu.base.TitleLayoutActivity;

public class AccountSubscribeActivity extends TitleLayoutActivity {

    private ImageView openWx, contacts, addFriends, subscribe, subscribeSelectedImg;
    private TextView openWxText, contactsText, addFriendsText, subscribeText, subscribeTip;
    private Button subscribeOpenWx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_subscribe);

        setTitleLayoutTitle(null, "订阅");
        showBackButton(null);

        initView();

        Intent intent = getIntent();
        if (intent.getStringExtra("account_wx_name") != null) {
            subscribeTip.setText("* 公众号：" + intent.getStringExtra("account_wx_name") + " 已经复制到剪贴板");
        }
        else {
            subscribeTip.setVisibility(View.GONE);
        }
    }

    private void initView() {
        openWx = (ImageView) findViewById(R.id.iv_open_wx_icon);
        contacts = (ImageView) findViewById(R.id.iv_contacts_icon);
        addFriends = (ImageView) findViewById(R.id.iv_add_friends_icon);
        subscribe = (ImageView) findViewById(R.id.iv_subscribe_icon);
        subscribeSelectedImg = (ImageView) findViewById(R.id.iv_subscribe_selectedImg);
        openWxText = (TextView) findViewById(R.id.tv_openwx_text);
        contactsText = (TextView) findViewById(R.id.tv_contacts_text);
        addFriendsText = (TextView) findViewById(R.id.tv_addfriends_text);
        subscribeText = (TextView) findViewById(R.id.tv_subscribe_text);
        subscribeTip = (TextView) findViewById(R.id.tv_subscribe_tip);
        subscribeOpenWx = (Button) findViewById(R.id.bt_subscribe_openwx);

        subscribe.setOnClickListener(clickListener);
        subscribeOpenWx.setOnClickListener(clickListener);
        openWx.setOnClickListener(clickListener);
        contacts.setOnClickListener(clickListener);
        addFriends.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_open_wx_icon:
                    openWx.setImageResource(R.mipmap.openwx_select);
                    openWxText.setTextColor(Color.parseColor("#64de4c"));
                    contacts.setImageResource(R.mipmap.contacts_icon);
                    contactsText.setTextColor(Color.parseColor("#515151"));
                    addFriends.setImageResource(R.mipmap.add_friends_icon);
                    addFriendsText.setTextColor(Color.parseColor("#515151"));
                    subscribe.setImageResource(R.mipmap.subscribe_icon);
                    subscribeText.setTextColor(Color.parseColor("#515151"));
                    subscribeSelectedImg.setImageResource(R.mipmap.open_wx);
                    break;
                case R.id.iv_contacts_icon:
                    contacts.setImageResource(R.mipmap.contacts_select);
                    contactsText.setTextColor(Color.parseColor("#64de4c"));
                    openWx.setImageResource(R.mipmap.open_wx_icon);
                    openWxText.setTextColor(Color.parseColor("#515151"));
                    addFriends.setImageResource(R.mipmap.add_friends_icon);
                    addFriendsText.setTextColor(Color.parseColor("#515151"));
                    subscribe.setImageResource(R.mipmap.subscribe_icon);
                    subscribeText.setTextColor(Color.parseColor("#515151"));
                    subscribeSelectedImg.setImageResource(R.mipmap.contacts);
                    break;
                case R.id.iv_add_friends_icon:
                    addFriends.setImageResource(R.mipmap.add_friends_select);
                    addFriendsText.setTextColor(Color.parseColor("#64de4c"));
                    contacts.setImageResource(R.mipmap.contacts_icon);
                    contactsText.setTextColor(Color.parseColor("#515151"));
                    openWx.setImageResource(R.mipmap.open_wx_icon);
                    openWxText.setTextColor(Color.parseColor("#515151"));
                    subscribe.setImageResource(R.mipmap.subscribe_icon);
                    subscribeText.setTextColor(Color.parseColor("#515151"));
                    subscribeSelectedImg.setImageResource(R.mipmap.add_friends);
                    break;
                case R.id.iv_subscribe_icon:
                    subscribe.setImageResource(R.mipmap.subscribe_icon_select);
                    subscribeText.setTextColor(Color.parseColor("#64de4c"));
                    addFriends.setImageResource(R.mipmap.add_friends_icon);
                    addFriendsText.setTextColor(Color.parseColor("#515151"));
                    contacts.setImageResource(R.mipmap.contacts_icon);
                    contactsText.setTextColor(Color.parseColor("#515151"));
                    openWx.setImageResource(R.mipmap.open_wx_icon);
                    openWxText.setTextColor(Color.parseColor("#515151"));
                    subscribeSelectedImg.setImageResource(R.mipmap.subscribe);
                    break;
                case R.id.bt_subscribe_openwx:
                    Intent intent = new Intent();
                    ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                    intent.setAction(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setComponent(cmp);
                    startActivityForResult(intent, 2);
                    break;
            }
        }
    };
}
