package com.caibo.weidu.main.account.accountdetail;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.caibo.weidu.R;
import com.caibo.weidu.base.TitleLayoutActivity;
import com.caibo.weidu.main.account.subscribe.AccountSubscribeActivity;
import com.caibo.weidu.util.JsonUtil;
import com.caibo.weidu.util.StringUtil;
import com.caibo.weidu.util.WDDialogUtil;
import com.caibo.weidu.util.WDImageLoaderUtil;
import com.caibo.weidu.util.WDRequest;
import com.caibo.weidu.widget.CopyPopupWindow;

import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class AccountDetailActivity extends TitleLayoutActivity implements WDRequest.WDRequestDelegate{

    private TextView accountName, accountWxNo, functionIntro, authenticationInfo, accountUrl;
    private CircleImageView accountImage;
    private ImageView scoreStar, like, subscribe;
    private LinearLayout likeLayout, subscribeLayout, urlLayout;

    private String accountId;
    private boolean favorite = false;
    private boolean isLoading = false;
    private Activity activity;

    public static final int AccountDetailActivityRequestCode = 10002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);

        setTitleLayoutTitle(null, getIntent().getStringExtra("account_name"));
        showBackButton(null);

        accountId = getIntent().getStringExtra("account_id");
        activity  = AccountDetailActivity.this;

        initView();

        initData();
    }

    private void initView() {
        accountImage = (CircleImageView) findViewById(R.id.civ_account_img);
        scoreStar = (ImageView) findViewById(R.id.iv_score_star);
        like = (ImageView) findViewById(R.id.iv_like);
        subscribe = (ImageView) findViewById(R.id.iv_subscribe);
        accountName = (TextView) findViewById(R.id.tv_account_name);
        accountWxNo = (TextView) findViewById(R.id.tv_account_wx_no);
        functionIntro = (TextView) findViewById(R.id.tv_function_introduction);
        authenticationInfo = (TextView) findViewById(R.id.tv_authentication_information);
        accountUrl = (TextView) findViewById(R.id.tv_account_url);

        likeLayout = (LinearLayout) findViewById(R.id.ll_like_layout);
        subscribeLayout = (LinearLayout) findViewById(R.id.ll_subscribe_layout);
        urlLayout = (LinearLayout) findViewById(R.id.ll_accountdetail_accounturl_layout);

        likeLayout.setOnClickListener(clickListener);
        subscribeLayout.setOnClickListener(clickListener);

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_like_layout:
                    if (favorite) {
                        WDDialogUtil.showLoadingDialog(AccountDetailActivity.this);
                        WDRequest request = new WDRequest(AccountDetailActivity.this);
                        request.setDelegate(AccountDetailActivity.this);
                        request.favorite_remove(accountId);
                    }
                    else {
                        WDDialogUtil.showLoadingDialog(AccountDetailActivity.this);
                        WDRequest request = new WDRequest(AccountDetailActivity.this);
                        request.setDelegate(AccountDetailActivity.this);
                        request.favorite(accountId);
                    }
                    break;
                case R.id.ll_subscribe_layout:
                    copy(accountWxNo);

                    Intent intent = new Intent(AccountDetailActivity.this, AccountSubscribeActivity.class);
                    intent.putExtra("account_wx_name", accountWxNo.getText());
                    startActivity(intent);
                    break;
            }
        }
    };

    private void copy(TextView tv) {
        ClipboardManager cm = (ClipboardManager) getSystemService(AccountDetailActivity.CLIPBOARD_SERVICE);
        cm.setText(tv.getText());
        Toast.makeText(AccountDetailActivity.this, tv.getText() + "已经复制到系统剪贴板", Toast.LENGTH_SHORT).show();
    }

    private void initData() {

        if (!isLoading) {
            isLoading = true;
            WDDialogUtil.showLoadingDialog(this);

            WDRequest request = new WDRequest(this);
            request.setDelegate(this);
            request.account_detail(accountId);

        }

    }

    @Override
    public void requestSuccess(WDRequest req, String data) {
        if (req.tag == WDRequest.Req_Tag.Tag_Account_Detail) {
            Log.i("Tag_Account_Detail", data);

            try {
                JSONObject jsonObject = new JSONObject(data);
                JSONObject dataJson = jsonObject.getJSONObject("data");
                WDImageLoaderUtil.displayImage(dataJson.getString("a_logo"), accountImage, R.mipmap.account_image);
                accountName.setText(dataJson.getString("a_name"));
                accountWxNo.setText(dataJson.getString("a_wx_no"));

                functionIntro.setText(StringUtil.isTrimBlank(dataJson.getString("a_desc")) ? "暂无介绍" : dataJson.getString("a_desc"));
                functionIntro.setOnLongClickListener(copyActionListener);

                authenticationInfo.setText(StringUtil.isTrimBlank(dataJson.getString("a_valid_reason")) ? "暂无介绍" : dataJson.getString("a_valid_reason"));
                authenticationInfo.setOnLongClickListener(copyActionListener);

                if (StringUtil.isTrimBlank(dataJson.getString("a_url"))) {
                    urlLayout.setVisibility(View.GONE);
                } else {
                    accountUrl.setText(dataJson.getString("a_url"));
                }

                if (dataJson.getInt("a_favorited") == 1) {
                    like.setImageResource(R.mipmap.like_select);
                    favorite = true;
                }

                switch(Integer.valueOf(dataJson.getString("a_rank"))) {
                    case 1:
                        scoreStar.setImageResource(R.mipmap.star_1);
                        break;
                    case 2:
                        scoreStar.setImageResource(R.mipmap.star_2);
                        break;
                    case 3:
                        scoreStar.setImageResource(R.mipmap.star_3);
                        break;
                    case 4:
                        scoreStar.setImageResource(R.mipmap.star_4);
                        break;
                    case 5:
                        scoreStar.setImageResource(R.mipmap.star_5);
                        break;
                }

            } catch (Exception e){
                e.printStackTrace();
            }

            isLoading = false;
            WDDialogUtil.dismissDialog(this);
        }
        else if (req.tag == WDRequest.Req_Tag.Tag_Favorite) {
            Log.i("Tag_Favorite", data);
            favorite = true;
            like.setImageResource(R.mipmap.like_select);
            activity.setResult(Activity.RESULT_OK);

            isLoading = false;
            WDDialogUtil.dismissDialog(this);
        }
        else if (req.tag == WDRequest.Req_Tag.Tag_Favorite_Remove) {
            Log.i("Tag_Favorite_Remove", data);
            favorite = false;
            like.setImageResource(R.mipmap.like_normal);
            activity.setResult(Activity.RESULT_OK);

            isLoading = false;
            WDDialogUtil.dismissDialog(this);
        }
    }

    @Override
    public void requestFail(WDRequest req, String message) {
        Log.i("account_detail", message);

        isLoading = false;
        if (req.tag == WDRequest.Req_Tag.Tag_Account_Detail) {
            WDDialogUtil.changeLoadingDialogToError(this, message, true, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    initData();
                }
            });
        }
        else {
            WDDialogUtil.changeLoadingDialogToError(this, message);
        }

    }


    private View.OnLongClickListener copyActionListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            CopyPopupWindow popupWindow = new CopyPopupWindow(AccountDetailActivity.this, ((TextView)v).getText().toString());
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new ColorDrawable());
            popupWindow.showPopupWindow(v);

            return true;
        }
    };
}
