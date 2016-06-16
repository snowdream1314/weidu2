package com.caibo.weidu.main.account.accountcategory;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.caibo.weidu.R;
import com.caibo.weidu.base.TitleLayoutActivity;
import com.caibo.weidu.bean.Child_Cat;
import com.caibo.weidu.main.account.accountcategory.fragment.AccountFragment;
import com.gxz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

public class AccountCatActivity extends TitleLayoutActivity{

    private List<Child_Cat> childCats = new ArrayList<Child_Cat>();
    private PagerSlidingTabStrip pagerSlidingTabStrip;
    private ViewPager viewPager;
    private List<AccountFragment> fragments = new ArrayList<AccountFragment>();
    private AccountCatFragmentAdapter accountCatFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_cat);

        setTitleLayoutTitle(null, getIntent().getStringExtra("category_name"));
        showBackButton(null);

        childCats = (ArrayList<Child_Cat>) getIntent().getSerializableExtra("child_cats");

        pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.pst_pagerSlidingTab);
        viewPager = (ViewPager) findViewById(R.id.vp_viewPager);

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

        //初始化分类下的第一个页面
        if(!fragments.isEmpty()) {
            Log.i("isEmpty", "false");
            fragments.get(0).initData();
            Log.i("initial", "initial");
        }
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
