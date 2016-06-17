package com.caibo.weidu.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.caibo.weidu.R;
import com.caibo.weidu.util.AppUtil;

/**
 * Created by snow 2016/6/17.
 */
public class CopyPopupWindow extends PopupWindow{

    private Context context;
    private String copyString;

    public CopyPopupWindow(Context context, String copyString) {
        super(context);
        this.context = context;
        this.copyString = copyString;

        init();
    }

    public CopyPopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        init();
    }

    public CopyPopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        init();
    }

    public CopyPopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;

        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.account_popupwindow, null);

        Button copy = (Button) view.findViewById(R.id.btn_copy);
        copy.setOnClickListener(copyAction);

        this.setWindowLayoutMode(AppUtil.dip2px((Activity)context, 49),AppUtil.dip2px((Activity)context, 35));
        this.setWidth(AppUtil.dip2px((Activity)context, 49));
        this.setHeight(AppUtil.dip2px((Activity)context, 35));

        setContentView(view);
    }

    private View.OnClickListener copyAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(copyString!=null){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    final android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    final android.content.ClipData clipData = android.content.ClipData.newPlainText("", copyString);
                    clipboardManager.setPrimaryClip(clipData);
                } else {
                    final android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboardManager.setText(copyString);
                }
            }
            Toast.makeText(context, "复制成功",Toast.LENGTH_SHORT).show();

            dismiss();
        }
    };


    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {

            this.showAsDropDown(parent, parent.getWidth()/2- AppUtil.dip2px((Activity)context, 50/2), -parent.getHeight()- AppUtil.dip2px((Activity)context,35));

        } else {
            this.dismiss();
        }
    }


}
