package com.caibo.weidu.main;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.caibo.weidu.R;
import com.caibo.weidu.main.account.AccountFragment;
import com.caibo.weidu.main.like.LikeFragment;
import com.caibo.weidu.main.more.MoreFragment;
import com.caibo.weidu.util.UserUtil;
import com.caibo.weidu.util.WDRequest;

public class MainActivity extends FragmentActivity implements WDRequest.WDRequestDelegate{

    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        initTabHost();

        if (!UserUtil.isRegistered(this)) {
            //注册
            WDRequest request = new WDRequest(this);
            request.setDelegate(this);
            request.register(UserUtil.getDeviceId(this));
        }
    }

    private void initTabHost() {
        FragmentTabHost.TabSpec accountTab = mTabHost.newTabSpec("account").setIndicator(initView("公众号", R.drawable.account_selector));
        mTabHost.addTab(accountTab, AccountFragment.class, null);

        FragmentTabHost.TabSpec likeTab = mTabHost.newTabSpec("like").setIndicator(initView("喜欢", R.drawable.like_selector));
        mTabHost.addTab(likeTab, LikeFragment.class, null);

        FragmentTabHost.TabSpec moreTab = mTabHost.newTabSpec("more").setIndicator(initView("更多", R.drawable.more_selector));
        mTabHost.addTab(moreTab, MoreFragment.class, null);
    }

    private View initView(String name, int drawableId) {
        View view = View.inflate(this, R.layout.layout_tabhost, null);
        TextView title = (TextView) view.findViewById(R.id.title);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        title.setText(name);
        image.setImageResource(drawableId);
        return view;
    }

    @Override
    public void requestSuccess(WDRequest req, String result) {

        if (req.tag == WDRequest.Req_Tag.TAG_REGISTER) {
            Log.i("TAG_REGISTER", result);
            UserUtil.setSession(this, result);
        }
    }

    @Override
    public void requestFail(WDRequest req, String message) {
        Log.i("requestFail", message);
    }
}
