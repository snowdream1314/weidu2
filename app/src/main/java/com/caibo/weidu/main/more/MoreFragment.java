package com.caibo.weidu.main.more;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.caibo.weidu.R;
import com.caibo.weidu.base.TitleLayoutFragment;
import com.caibo.weidu.bean.More;
import com.caibo.weidu.main.web.WebActivity;
import com.caibo.weidu.viewholder.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends TitleLayoutFragment{

    private View rootView;
    private ListView mListView;
    private List<More> mores = new ArrayList<More>();
    private MoreAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if( rootView == null ) {
            rootView = inflater.inflate(R.layout.fragment_more, container, false);

            View view = rootView;
            setTitleLayoutTitle(view, "更多");

            initItems();

            mListView = (ListView) rootView.findViewById(R.id.lv_more);
            adapter = new MoreAdapter(getContext(), mores);
            mListView.setAdapter(adapter);

            mListView.setOnItemClickListener(clickListener);
        }

        return rootView;
    }

    private void initItems() {
        mores.clear();

        More tool = new More("工具", 0, 0);
        mores.add(tool);

        More free_collect = new More("免费收录", R.mipmap.free_collect, R.mipmap.tag_more);
        mores.add(free_collect);

        More about = new More("关于", 0, 0);
        mores.add(about);

        More about_us = new More("关于我们", R.mipmap.about_us, R.mipmap.tag_more);
        mores.add(about_us);

        More cooperation = new More("商务合作", R.mipmap.business_cooperation, R.mipmap.tag_more);
        mores.add(cooperation);

        More donation = new More("给我点爱", R.mipmap.donation, R.mipmap.tag_more);
        mores.add(donation);

        More feed_back = new More("意见反馈", R.mipmap.feed_back, R.mipmap.tag_more);
        mores.add(feed_back);
    }


    private AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 1:
                    Intent intent = new Intent(getActivity(), WebActivity.class);
                    intent.putExtra("title", "免费收录");
                    intent.putExtra("url", "http://wx.xiyiyi.com/Mobile/Account/submit");
                    startActivity(intent);
                    break;
                case 3:
                    Intent about_us = new Intent(getActivity(), WebActivity.class);
                    about_us.putExtra("title", "关于我们");
                    about_us.putExtra("url", "http://wx.xiyiyi.com/Mobile/About/aboutus");
                    startActivity(about_us);
                    break;
                case 4:
                    Intent cooperation = new Intent(getActivity(), WebActivity.class);
                    cooperation.putExtra("title", "商务合作");
                    cooperation.putExtra("url", "http://wx.xiyiyi.com/Mobile/About/business");
                    startActivity(cooperation);
                    break;
                case 5:
//                    Intent intent = new Intent(getActivity(), WebActivity.class);
//                    intent.putExtra("title", "给我点爱");
//                    intent.putExtra("url", "http://wx.xiyiyi.com/Mobile/Account/submit");
//                    startActivity(intent);
                    break;
                case 6:
                    Intent feed_back = new Intent(getActivity(), WebActivity.class);
                    feed_back.putExtra("title", "意见反馈");
                    feed_back.putExtra("url", "http://wx.xiyiyi.com/Mobile/Feedback/index");
                    startActivity(feed_back);
                    break;
            }
        }
    };


    private class MoreAdapter extends BaseAdapter {
        private Context context;
        private List<More> mores;

        public MoreAdapter(Context context, List<More> mores) {
            this.context = context;
            this.mores = mores;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return mores.get(position);
        }

        @Override
        public int getCount() {
            return mores.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.more_cell_listview, null);
            }

            More more = mores.get(position);

            TextView title = (TextView) ViewHolder.get(convertView, R.id.tv_more_title);
            ImageView image = (ImageView) ViewHolder.get(convertView, R.id.iv_more_image);
            ImageView arrow = (ImageView) ViewHolder.get(convertView, R.id.iv_more_arrow);

            title.setText(more.getTitle());
            image.setImageResource(more.getImageId());
            arrow.setImageResource(more.getArrowId());

            if (more.getArrowId() == 0 || more.getImageId() == 0) {
                arrow.setVisibility(View.GONE);
                image.setVisibility(View.GONE);
                convertView.setClickable(true);
            } else {
                arrow.setVisibility(View.VISIBLE);
                image.setVisibility(View.VISIBLE);
                convertView.setClickable(false);
            }

            return convertView;
        }

    }
}
