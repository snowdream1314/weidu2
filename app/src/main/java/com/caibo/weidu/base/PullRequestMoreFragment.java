package com.caibo.weidu.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.caibo.weidu.R;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class PullRequestMoreFragment extends TitleLayoutFragment {
    protected int current_page = 0;
    protected int total_page = -1;
    protected int total_count = 0;

    protected int page_size = 20;
    protected boolean empty = false;
    protected boolean isLoading = false;

    protected static final int Cell_Type_Empty = -1;
    protected static final int Cell_Type_Normal = 1;
    protected static final int Cell_Type_Loading = 0;

    //重新加载
    public abstract void reloadData();

    //加载更多
    public abstract void loadMoreData();

    protected PullRefreshLayout refreshLayout;

    protected void initRefreshLayout(View view) {
        initRefreshLayout(view, R.id.prl_refresh_layout);
    }

    private void initRefreshLayout(View view, int resId) {
        refreshLayout = (PullRefreshLayout) view.findViewById(resId);
        if (refreshLayout != null) {
            refreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_SMARTISAN);
            refreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    reloadData();
                }
            });
        }

    }

    protected void setRefreshing(boolean refreshing) {
        if (refreshLayout != null) {
            refreshLayout.setRefreshing(refreshing);
        }
    }


    protected AbsListView.OnScrollListener pullToLoadMoreDataListener = new AbsListView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            // 满足下列条件后可开始加载下一页
            if (!isLoading && firstVisibleItem + visibleItemCount > totalItemCount - 2) {
                if (current_page>=1 && total_page>current_page) {
                    loadMoreData();
                }
            }
        }
    };


    public interface PullRequestMoreCompleteListener{
        public void complete();
    }

}
