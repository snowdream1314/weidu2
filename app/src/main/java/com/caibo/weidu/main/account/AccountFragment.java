package com.caibo.weidu.main.account;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.caibo.weidu.R;
import com.caibo.weidu.base.TitleLayoutFragment;
import com.caibo.weidu.main.account.gridview.NoScrollGridView;
import com.caibo.weidu.util.WDRequest;
import com.caibo.weidu.bean.Accounts;
import com.caibo.weidu.bean.Recommend_Account;
import com.caibo.weidu.main.account.accountcategory.AccountCatActivity;
import com.caibo.weidu.main.account.accountdetail.AccountDetailActivity;
import com.caibo.weidu.util.JsonUtil;
import com.caibo.weidu.util.WDImageLoaderUtil;
import com.caibo.weidu.viewholder.ViewHolder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends TitleLayoutFragment implements WDRequest.WDRequestDelegate{

    private View rootView;
    private ListView mListView;
    private List<Accounts> accountsList = new ArrayList<Accounts>();
    private AccountsAdapter accountsAdapter;
    private boolean initial = false;

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
            rootView = inflater.inflate(R.layout.fragment_account, null);

            View view = rootView;
            setTitleLayoutTitle(view, "公众号");

            initData();

            mListView = (ListView)rootView.findViewById(R.id.lv_account);
            accountsAdapter = new AccountsAdapter(getActivity(), accountsList);
            mListView.setAdapter(accountsAdapter);
        }

        ViewGroup parent = (ViewGroup)rootView.getParent();
        if(parent != null) {
            parent.removeView(rootView);
        }

        return rootView;
    }

    private void initData() {
        if (!initial) {
            initial = true;
            WDRequest request = new WDRequest(getActivity());
            request.setDelegate(this);
            request.account_category();
        }
    }

    @Override
    public void requestSuccess(WDRequest req, String data) {
        if (req.tag == WDRequest.Req_Tag.Tag_Account_Categorys) {
            Log.i("Account_Category", data);

            try {
                JSONObject jsonObject = new JSONObject(data);

                Type accountsType = new TypeToken<List<Accounts>>(){}.getType();
                List<Accounts> tmpAccounts = JsonUtil.json2Any(jsonObject.getString("data"), accountsType);

                if (tmpAccounts != null) {
                    accountsList.addAll(tmpAccounts);
                }

                accountsAdapter.notifyDataSetChanged();

                Log.i("accounts", accountsList.get(0).getAc_name().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void requestFail(WDRequest req, String message) {
        Log.i("Account_Category", "fail");
    }


    private class AccountsAdapter extends BaseAdapter {

        private Context mContext;
        private List<Accounts> accountsList;

        public AccountsAdapter (Context context, List<Accounts> accounts) {
            this.mContext = context;
            this.accountsList = accounts;
        }

        @Override
        public int getCount() {
            return accountsList.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            if (accountsList == null) { return 0; }
            return accountsList.get(position);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.account_cell_listview, null);
            }
            final Accounts accounts = accountsList.get(position);
            TextView account_category = (TextView) ViewHolder.get(convertView, R.id.tv_account_category);
            ImageView tab_img = (ImageView) ViewHolder.get(convertView, R.id.iv_tab_image);
            View tab_line = (View) ViewHolder.get(convertView, R.id.v_tab_line);
            NoScrollGridView gridView = (NoScrollGridView) ViewHolder.get(convertView, R.id.gv_gridview);
            View wave_line = (View) ViewHolder.get(convertView, R.id.v_wave_line);
            LinearLayout category = (LinearLayout) ViewHolder.get(convertView, R.id.ll_listview_item);

            if (accounts.getAc_name().equals("热门推荐")) {
                category.setBackgroundColor(Color.parseColor("#ffffff"));
                tab_img.setVisibility(View.GONE);
                tab_line.setVisibility(View.GONE);
                category.setClickable(false);
                wave_line.setVisibility(View.VISIBLE);
            }
            else {
                category.setBackgroundColor(Color.parseColor("#f4f4f4"));
                tab_img.setVisibility(View.VISIBLE);
                tab_line.setVisibility(View.VISIBLE);
                category.setClickable(true);
                wave_line.setVisibility(View.GONE);
            }

            account_category.setText(accounts.getAc_name());
            category.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AccountCatActivity.class);
                    intent.putExtra("category_name", accounts.getAc_name());
                    intent.putExtra("child_cats", (Serializable) accounts.getChildCats());
                    startActivity(intent);
                }
            });

            GridViewAdapter gridViewAdapter = new GridViewAdapter(mContext, accounts.getRecommendAccount());
            gridView.setAdapter(gridViewAdapter);

            return convertView;
        }
    }


    private class GridViewAdapter extends BaseAdapter {

        private Context mContext;
        private List<Recommend_Account> recommendAccounts;

        public GridViewAdapter(Context context, List<Recommend_Account> accounts) {
            this.mContext = context;
            this.recommendAccounts = accounts;
        }

        @Override
        public int getCount() {
            return recommendAccounts.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            if (recommendAccounts == null) {
                return null;
            }

            return  recommendAccounts.get(position);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.account_cell_gridview, null);
            }

            final Recommend_Account recommendAccount = recommendAccounts.get(position);
            CircleImageView accountImage = (CircleImageView) ViewHolder.get(convertView, R.id.civ_account_image);
            TextView accountName = (TextView) ViewHolder.get(convertView, R.id.tv_account_name);

            WDImageLoaderUtil.displayImage(recommendAccount.getA_logo().toString(), accountImage, R.mipmap.account_image);
            accountName.setText(recommendAccount.getA_name());

            accountImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AccountDetailActivity.class);
                    intent.putExtra("account_name", recommendAccount.getA_name());
                    intent.putExtra("account_id", recommendAccount.getA_id());
                    startActivity(intent);
                }
            });

            return convertView;
        }
    }

}
