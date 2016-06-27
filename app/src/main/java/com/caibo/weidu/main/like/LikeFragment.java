package com.caibo.weidu.main.like;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.caibo.weidu.R;
import com.caibo.weidu.base.PullRequestMoreFragment;
import com.caibo.weidu.bean.Account;
import com.caibo.weidu.main.account.accountdetail.AccountDetailActivity;
import com.caibo.weidu.util.AppUtil;
import com.caibo.weidu.util.JsonUtil;
import com.caibo.weidu.util.WDDialogUtil;
import com.caibo.weidu.util.WDImageLoaderUtil;
import com.caibo.weidu.util.WDRequest;
import com.caibo.weidu.viewholder.EmptyView;
import com.caibo.weidu.viewholder.ViewHolder;
import com.google.gson.reflect.TypeToken;
import com.snowdream.enhancedpulltorefreshlistview.EnhancedPullToRefreshListView;
import com.snowdream.enhancedpulltorefreshlistview.pulltorefresh.interfaces.IXListViewListener;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.bean.SwipeMenu;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.bean.SwipeMenuItem;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.interfaces.OnMenuItemClickListener;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.interfaces.SwipeMenuCreator;
import edu.swu.pulltorefreshswipemenulistview.library.util.RefreshTime;

/**
 * A simple {@link Fragment} subclass.
 */
public class LikeFragment extends PullRequestMoreFragment implements WDRequest.WDRequestDelegate, IXListViewListener {

    private View rootView;
//    private PullToRefreshSwipeMenuListView mListView;
    private EnhancedPullToRefreshListView mListView;
    private List<Account> accounts = new ArrayList<Account>();
    private Adapter adapter;
    private boolean initial = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if ( rootView == null ) {
            rootView = inflater.inflate(R.layout.fragment_like, null);

            View view = rootView;
            setTitleLayoutTitle(view, "喜欢");

            initData();

            mListView = (EnhancedPullToRefreshListView) rootView.findViewById(R.id.lv_like);
            adapter = new Adapter(getActivity(), accounts);
            mListView.setAdapter(adapter);

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Account account = accounts.get(position - 1);
                    Log.i("position", String.valueOf(position));
                    Intent intent = new Intent(getActivity(), AccountDetailActivity.class);
                    intent.putExtra("account_name", account.getA_name());
                    intent.putExtra("account_id", account.getA_id());
                    startActivityForResult(intent, AccountDetailActivity.AccountDetailActivityRequestCode);
                }
            });

            //下拉刷新
//            initRefreshLayout(rootView);

            //加载更多
            mListView.setOnScrollListener(pullToLoadMoreDataListener);

            mListView.setPullRefreshEnable(true);//开启下拉刷新
            mListView.setPullLoadEnable(false);//关闭加载更多，使用滚动监听
            mListView.disableSwipeToDismiss();//禁止滑动删除
//
            mListView.setXListViewListener(LikeFragment.this);

            mListView.setDismissCallback(new EnhancedPullToRefreshListView.OnDismissCallback() {
                @Override
                public EnhancedPullToRefreshListView.Undoable onDismiss(EnhancedPullToRefreshListView listView, int position) {
                    final int deletePosition = position;
                    final Account account = accounts.get(position);
                    final String account_id = accounts.get(position).getA_id();
                    if (deletePosition<accounts.size()) {
                        accounts.remove(deletePosition);
                        empty = accounts.size() == 0 ? true : false;
                        adapter.notifyDataSetChanged();
                    }

                    return new EnhancedPullToRefreshListView.Undoable() {
                        @Override
                        public void undo() {
                            accounts.add(deletePosition, account);
                            empty = accounts.size() == 0 ? true : false;
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public String getTitle() {
                            return null;
                        }

                        @Override
                        public void discard() {
                            WDRequest request = new WDRequest(getContext());
                            request.setDelegate(LikeFragment.this);
                            request.favorite_remove(account_id);
                        }
                    };
                }
            });
            mListView.enableSwipeToDismiss();
            mListView.setSwipeDirection(EnhancedPullToRefreshListView.SwipeDirection.BOTH);
            mListView.setUndoHideDelay(2500);
            mListView.setUndoStyle(EnhancedPullToRefreshListView.UndoStyle.COLLAPSED_POPUP);
            mListView.setRequireTouchBeforeDismiss(false);
//
//            SwipeMenuCreator creator = new SwipeMenuCreator() {
//                @Override
//                public void create(SwipeMenu menu) {
//                    SwipeMenuItem delete = new SwipeMenuItem(getContext());
//                    delete.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
//                    delete.setIcon(R.mipmap.like_delete_icon);
//                    delete.setWidth(AppUtil.dip2px(getActivity(), 120));
//                    menu.addMenuItem(delete);
//                }
//            };
//            mListView.setMenuCreator(creator);
//
//            mListView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
//                @Override
//                public void onMenuItemClick(int position, SwipeMenu menu, int index) {
//                    Account account = accounts.get(position);
//                    WDRequest request = new WDRequest(getContext());
//                    request.setDelegate(LikeFragment.this);
//                    request.favorite_remove(account.getA_id());
//                    accounts.remove(position);
//                    empty = accounts.size() == 0 ? true : false;
//                    adapter.notifyDataSetChanged();
//                }
//            });

        }

        ViewGroup parent = (ViewGroup)rootView.getParent();
        if(parent != null) {
            parent.removeView(rootView);
        }

        return rootView;
    }

    public void onRefresh() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm", Locale.getDefault());
        RefreshTime.setRefreshTime(getContext(), df.format(new Date()));
        mListView.setRefreshTime(RefreshTime.getRefreshTime(getContext()));
        reloadData();
    }

    public void onLoadMore() {
//        if (current_page>=1 && total_page>current_page) {
//            mListView.setRefreshTime(RefreshTime.getRefreshTime(getContext()));
//            loadMoreData();
//        }
    }

    private void initData() {
        if (!initial) {
            initial = true;
            loadData();
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

    private void loadData() {
        if (!isLoading) {
            isLoading = true;
            WDDialogUtil.showLoadingDialog(getContext());
            WDRequest request = new WDRequest(getActivity());
            request.setDelegate(this);
            request.favorite_list(current_page + 1);
        }
    }

    @Override
    public void requestSuccess(WDRequest req, String data) {
        if (req.tag == WDRequest.Req_Tag.Tag_Favorite_List) {
            Log.i("favorite_list", data);

            try {
                JSONObject jsonObject = new JSONObject(data);
                total_count = jsonObject.getJSONObject("data").getInt("total_count");
                Log.i("total_count", String.valueOf(total_count));
                total_page = (int) Math.ceil(total_count*1.0/20);
                empty = total_page == 0 ? true : false;
                current_page ++;
                Log.i("current_page",String.valueOf(current_page));
                if (current_page == 1) {
                    accounts.clear();
                }

                Type accountsType = new TypeToken<List<Account>>(){}.getType();
                List<Account> tmpAccounts = JsonUtil.json2Any(jsonObject.getJSONObject("data").getString("favorite_accounts"), accountsType);
                if (tmpAccounts != null) {
                    accounts.addAll(tmpAccounts);
                }

                Log.i("accounts", String.valueOf(accounts.size()));
                adapter.notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }

            mListView.stopRefresh();
            mListView.stopLoadMore();
            isLoading = false;
            setRefreshing(false);
            WDDialogUtil.dismissDialog(getContext());
        }
        else if (req.tag == WDRequest.Req_Tag.Tag_Favorite_Remove) {
            Log.i("favorite_remove", data);

            isLoading = false;
            setRefreshing(false);
            WDDialogUtil.dismissDialog(getContext());
        }
    }

    @Override
    public void requestFail(WDRequest req, String message) {
        Log.i("favorite_list", "fail");

        mListView.stopRefresh();
        mListView.stopLoadMore();
        isLoading = false;
        setRefreshing(false);
        WDDialogUtil.changeLoadingDialogToError(getContext(), message, true, new SweetAlertDialog.OnSweetClickListener() {
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
                View emptyView = new EmptyView(mContext, R.mipmap.like_none, "尼玛？\n 你都没有喜欢的公众号");
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            setRefreshing(true);
            reloadData();
        }
    }
}
