package com.caibo.weidu.main;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.caibo.weidu.R;
import com.caibo.weidu.main.account.AccountFragment;
import com.caibo.weidu.main.like.LikeFragment;
import com.caibo.weidu.main.more.MoreFragment;
import com.caibo.weidu.util.UserUtil;
import com.caibo.weidu.util.WDRequest;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONObject;

public class MainActivity extends FragmentActivity implements WDRequest.WDRequestDelegate{

    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        initTabHost();

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(MainActivity.this));

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
    public void requestSuccess(WDRequest req, String data) {

        if (req.tag == WDRequest.Req_Tag.Tag_Register) {
            Log.i("TAG_REGISTER", data);

            try {
                JSONObject jsonObject = new JSONObject(data);
                Log.i("session", jsonObject.getJSONObject("data").getString("session"));
                UserUtil.setSession(this, jsonObject.getJSONObject("data").getString("session"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void requestFail(WDRequest req, String message) {
        Log.i("requestFail", message);
    }


    //双击退出程序
    private static boolean isExit = false;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (!isExit) {

                isExit = true;
                Toast.makeText(MainActivity.this, "再按一次后退键退出程序", Toast.LENGTH_SHORT).show();

                new CountDownTimer(2200, 2200) {
                    @Override
                    public void onTick(long millisUntilFinished) {}

                    @Override
                    public void onFinish() { isExit = false; }
                }.start();

            } else {
                finish();
            }

            return false;
        }

        return super.onKeyUp(keyCode, event);
    }
}
