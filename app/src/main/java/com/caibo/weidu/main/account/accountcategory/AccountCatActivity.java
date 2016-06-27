package com.caibo.weidu.main.account.accountcategory;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caibo.weidu.R;
import com.caibo.weidu.base.TitleLayoutActivity;
import com.caibo.weidu.bean.Child_Cat;
import com.caibo.weidu.main.account.accountcategory.fragment.AccountFragment;
import com.caibo.weidu.util.AppUtil;
import com.gxz.PagerSlidingTabStrip;
import com.gxz.library.StickyNavLayout;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class AccountCatActivity extends TitleLayoutActivity{

    private List<Child_Cat> childCats = new ArrayList<Child_Cat>();
    private PagerSlidingTabStrip pagerSlidingTabStrip;
    private ViewPager viewPager;
    private List<AccountFragment> fragments = new ArrayList<AccountFragment>();
    private AccountCatFragmentAdapter accountCatFragmentAdapter;

    private StickyNavLayout stickyNavLayout;
    private PtrClassicFrameLayout mPtrFrame;
//    private TextView topTextView;
    private int i = 1;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_cat);

        setTitleLayoutTitle(null, getIntent().getStringExtra("category_name"));
        showBackButton(null);

        stickyNavLayout = (StickyNavLayout) findViewById(R.id.id_stick);
        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.store_house_ptr_frame);
        childCats = (ArrayList<Child_Cat>) getIntent().getSerializableExtra("child_cats");

        pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.id_stickynavlayout_indicator);
        viewPager = (ViewPager) findViewById(R.id.id_stickynavlayout_viewpager);

        stickyNavLayout.setTopViewHeight(50+ AppUtil.dip2px(AccountCatActivity.this, 10), 0 );

        fragments.clear();
        for (Child_Cat childCat : childCats) {
            AccountFragment fragment = AccountFragment.instance(AccountCatActivity.this, childCat.getAc_id(), childCat.getAc_name());
            fragments.add(fragment);
        }

        accountCatFragmentAdapter = new AccountCatFragmentAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(accountCatFragmentAdapter);
        viewPager.addOnPageChangeListener(pageChangeListener);
        viewPager.setOffscreenPageLimit(fragments.size());
        pagerSlidingTabStrip.setViewPager(viewPager);

        container = (LinearLayout) findViewById(R.id.container_merchant_detail);
        TextView textView = new TextView(this);
        textView.setText("测试一下");
        textView.setTextColor(Color.parseColor("#000000"));
        textView.setTextSize(AppUtil.dip2px(AccountCatActivity.this, 10));
        textView.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        textView.setHeight(AppUtil.dip2px(AccountCatActivity.this, 50));
        container.addView(textView);

        //初始化分类下的第一个页面
        if(!fragments.isEmpty()) {
            Log.i("isEmpty", "false");
            fragments.get(0).initData();
            Log.i("initial", "initial");
        }

        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return stickyNavLayout.getScrollY() == 0;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                TextView textView = new TextView(AccountCatActivity.this);
                textView.setText("刷新。。。" + i);
                textView.setTextColor(Color.parseColor("#000000"));
                textView.setTextSize(AppUtil.dip2px(AccountCatActivity.this, 16));
                textView.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                textView.setHeight(AppUtil.dip2px(AccountCatActivity.this, 50));
                container.addView(textView);
                fragments.get(0).initData();
                i = i + 1;
                mPtrFrame.refreshComplete();
            }
        });

        // the following are default settings
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);

    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            FLDLog.e("onPageScrolled:"+position+" positionOffset:"+positionOffset+" positionOffsetPixels:"+positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            Log.i("onPageSelected", "onPageSelected:"+position);
            fragments.get(position).initData();
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            Log.i("PageScrollStateChanged", "onPageScrollStateChanged:"+state);
        }
    };


    private class AccountCatFragmentAdapter extends FragmentPagerAdapter {
        private List<? extends Fragment> fragments;

        public AccountCatFragmentAdapter(FragmentManager fm, List<? extends Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() { return fragments.size(); }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments != null && position < fragments.size() ? childCats.get(position).getAc_name() : "";
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

    }
}
