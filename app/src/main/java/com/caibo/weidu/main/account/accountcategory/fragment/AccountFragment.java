package com.caibo.weidu.main.account.accountcategory.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.caibo.weidu.R;
import com.caibo.weidu.base.PullRequestMoreFragment;
import com.caibo.weidu.bean.Account;
import com.caibo.weidu.main.account.accountdetail.AccountDetailActivity;
import com.caibo.weidu.util.JsonUtil;
import com.caibo.weidu.util.WDDialogUtil;
import com.caibo.weidu.util.WDImageLoaderUtil;
import com.caibo.weidu.util.WDRequest;
import com.caibo.weidu.viewholder.EmptyView;
import com.caibo.weidu.viewholder.ViewHolder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends PullRequestMoreFragment implements WDRequest.WDRequestDelegate{
    private Context mContext;
    private String ac_id;
    private String ac_name;
    private boolean initial = false;

    private ListView mListView;
    private Adapter adapter;
    private List<Account> accounts = new ArrayList<Account>();

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment instance(Context context, String ac_id, String ac_name) {
        AccountFragment fragment = new AccountFragment();
        fragment.mContext = context;
        fragment.ac_id = ac_id;
        fragment.ac_name = ac_name;

        return  fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.accountcat_cell_fragment, null);

        mListView = (ListView) view.findViewById(R.id.id_stickynavlayout_innerscrollview);
        adapter = new Adapter(mContext, accounts);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Account account = accounts.get(position);
                Intent intent = new Intent(mContext, AccountDetailActivity.class);
                intent.putExtra("account_name", account.getA_name());
                intent.putExtra("account_id", account.getA_id());
                startActivity(intent);
            }
        });

        //下拉刷新
//        initRefreshLayout(view);

        //加载更多
        mListView.setOnScrollListener(pullToLoadMoreDataListener);

        return view;
    }

    public void initData() {
        Log.i("initData", "initData");
        if (!initial) {
            initial = true;
            loadData();
        }
    }

    public void loadData() {
        if (!isLoading) {
            isLoading = true;
            WDDialogUtil.showLoadingDialog(mContext);
            WDRequest request = new WDRequest(mContext);
            request.setDelegate(AccountFragment.this);
            request.category_accounts(ac_id, current_page + 1);
        }
    }

    @Override
    public void reloadData() {
        current_page = 0;
        loadData();
    }

    @Override
    public void loadMoreData() {
        loadData();
    }

    @Override
    public void requestSuccess(WDRequest req, String data) {
        if (req.tag == WDRequest.Req_Tag.Tag_Category_Accounts) {
            Log.i("category_accounts", data);

            try {
                JSONObject jsonObject = new JSONObject(data);
                total_count = jsonObject.getJSONObject("data").getInt("total_count");
                Log.i("total_count", String.valueOf(total_count));
                total_page = (int) Math.ceil(total_count*1.0/20);
                Log.i("total_page", String.valueOf(total_page));

                empty = total_page == 0 ? true : false;
                current_page ++;

                if (current_page == 1) {
                    accounts.clear();
                }

                Type accountsType = new TypeToken<List<Account>>(){}.getType();
                List<Account> tmpAccounts = JsonUtil.json2Any(jsonObject.getJSONObject("data").getString("accounts"), accountsType);
                if (tmpAccounts != null) {
                    accounts.addAll(tmpAccounts);
                }

                Log.i("accounts", String.valueOf(accounts.size()));
                adapter.notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }

            isLoading = false;
            setRefreshing(false);
            WDDialogUtil.dismissDialog(getContext());
        }
    }

    @Override
    public void requestFail(WDRequest req, String message) {
        Log.i("favorite_list", "fail");

        isLoading = false;
        setRefreshing(false);
        WDDialogUtil.changeLoadingDialogToError(mContext, message, true, new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                reloadData();
            }
        });
    }


    private class Adapter extends BaseAdapter {
        private Context mContext;
        private List<Account> accounts;

        public Adapter(Context context, List<Account> accounts) {
            this.mContext = context;
            this.accounts = accounts;
        }

        @Override
        public int getCount() {
            if (empty) {
                return 1;
            }

            return accounts.size();
        }

        @Override
        public Object getItem(int position) {
            return accounts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            if (empty) {
                mListView.setDividerHeight(0);
                return Cell_Type_Empty;
            }

            if (position < accounts.size()) {
                mListView.setDividerHeight(1);
                return Cell_Type_Normal;
            } else {
                return Cell_Type_Loading;
            }
        }

        @Override
        public int getViewTypeCount() {
            return 3;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int type = getItemViewType(position);
            if (type == Cell_Type_Empty) {
                View emptyView = new EmptyView(mContext, R.mipmap.like_none, "该分类下暂无公众号");
                return emptyView;
            }
            else if (type == Cell_Type_Normal){
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.like_cell_listview, null);
                }
                Account account = accounts.get(position);

                CircleImageView accountImage = (CircleImageView) ViewHolder.get(convertView, R.id.civ_account_image);
                TextView accountName = (TextView) ViewHolder.get(convertView, R.id.tv_account_name);
                TextView accountWxNo = (TextView) ViewHolder.get(convertView, R.id.tv_account_wx_no);
                ImageView scoreStar = (ImageView) ViewHolder.get(convertView, R.id.iv_score_star);
                TextView accountNotes = (TextView) ViewHolder.get(convertView, R.id.tv_account_notes);

                WDImageLoaderUtil.displayImage(account.getA_logo(), accountImage, R.mipmap.account_image);
                accountName.setText(account.getA_name());

                if (account.getA_wx_no().length() > 10) {
                    accountWxNo.setText(account.getA_wx_no().substring(0, 10) + "...");
                } else {
                    accountWxNo.setText(account.getA_wx_no());
                }
                accountNotes.setText(account.getA_desc());

                switch (Integer.valueOf(account.getA_rank())) {
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
                return convertView;
            }
            else {
                return LayoutInflater.from(mContext).inflate(R.layout.cell_loading, null);
            }

        }
    }

}
