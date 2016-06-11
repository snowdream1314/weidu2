package com.caibo.weidu.viewholder;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caibo.weidu.R;

/**
 * Created by snow on 2016/6/11.
 */
public class EmptyView extends LinearLayout{

    public EmptyView(Context context, int picId, String content) {
        super(context);
        initView(picId, content);
    }

    public EmptyView(Context context, int picId, String content, int bgId) {
        super(context);
        initView(picId, content, bgId);
    }

    private void initView(int picId, String content) {
        initView(picId, content, 0);
    }

    private void initView(int picId, String content, int bgId) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.cell_empty, this, true);

        ImageView likeNoneImage = (ImageView) view.findViewById(R.id.iv_likeNone);
        TextView likeNoneText = (TextView) view.findViewById(R.id.tv__likeNone);
        LinearLayout likeNone = (LinearLayout) view.findViewById(R.id.ll_likenone_layout);

        likeNoneImage.setImageResource(picId);
        likeNoneText.setText(content);

        if (bgId != 0) {
            likeNone.setBackgroundResource(bgId);
        }
    }
}
