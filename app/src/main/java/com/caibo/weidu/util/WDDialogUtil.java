package com.caibo.weidu.util;

import android.content.Context;
import android.content.DialogInterface;

import java.util.LinkedList;
import java.util.Queue;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by snow on 2016/6/16.
 */
public class WDDialogUtil {

    //通过队列管理显示过的dialog
    private static Queue<SweetAlertDialog> dialogs = new LinkedList<SweetAlertDialog>();

    /*
    * 显示提示对话框
    * @param content
    * */
    public static void showCommonDialog(Context context, String content) {

        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setTitleText("提示");
        dialog.setContentText(content);
        dialog.setOnDismissListener(dismissListener);
        dialog.show();

        dialogs.offer(dialog);
    }


    /*
    * 显示带标题的对话框
    * @params title
    * @params content
    * */
    public static void showCommonDialog(Context context, String title, String content) {
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setTitleText(title);
        dialog.setContentText(content);
        dialog.setOnDismissListener(dismissListener);
        dialog.show();

        dialogs.offer(dialog);
    }


    /*
    * 显示进度条对话框
    * */
    public static void showLoadingDialog(Context context) {
        showLoadingDialog(context, "加载中...");
    }

    public static void showLoadingDialog(Context context, boolean cancleable) {
        showLoadingDialog(context, "加载中...", cancleable);
    }

    public static void showLoadingDialog(Context context, String str) {
        showLoadingDialog(context, str, true);
    }

    public static void showLoadingDialog(Context context, String str, boolean cancelable) {
        dismissDialog(context);
        try {

            SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            dialog.setTitleText(str);
            dialog.setCancelable(cancelable);
            dialog.setOnDismissListener(dismissListener);
            dialog.show();

            dialogs.offer(dialog);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
    * 显示成功对话框
    * @params str
    * */
    public static void showSuccessDialog(Context context, String str) {
        dismissDialog(context);

        try {
            SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
            dialog.setTitleText("成功");
            dialog.setContentText(str);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setOnDismissListener(dismissListener);
            dialog.show();

            dialogs.offer(dialog);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void changeLoadingDialogToSuccess(Context context, String content) {
        changeLoadingDialogToSuccess(context, content, null);
    }

    public static void changeLoadingDialogToSuccess(Context context, String content, SweetAlertDialog.OnSweetClickListener confirmClickListener) {
        SweetAlertDialog dialog = dialogs.peek();

        if (dialog == null) {
            dialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
            dialog.show();

            dialogs.offer(dialog);
        }

        dialog.setContentText(content);
        dialog.setTitleText("成功");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnDismissListener(dismissListener);
        dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
        dialog.setConfirmClickListener(confirmClickListener);
    }


    /*
    * 显示错误对话框
    * */
    public static void showErrorDialog(Context context, String str) {
        showErrorDialog(context, str, null);
    }

    public static void showErrorDialog(Context context, String str, SweetAlertDialog.OnSweetClickListener confirmClickListener) {
        showErrorDialog(context, str, false, confirmClickListener);
    }

    public static void showErrorDialog(Context context, String str, boolean showRetry, SweetAlertDialog.OnSweetClickListener confirmClickListener) {
        dismissDialog(context);

        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        dialog.setTitleText("未成功");
        dialog.setContentText(str);

        dialog.setOnDismissListener(dismissListener);

        if (showRetry) {
            dialog.setConfirmText("重试");
            dialog.setCancelText("好的");
        }

        if (confirmClickListener != null) {
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
        }
        else {
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
        }

        dialog.show();

        dialogs.offer(dialog);
    }


    public static void changeLoadingDialogToError(Context context, String content) {
        changeLoadingDialogToError(context, content, false, null);
    }

    public static void changeLoadingDialogToError(Context context, String content, boolean showRetry, SweetAlertDialog.OnSweetClickListener confirmClickListener) {

        try {
            SweetAlertDialog dialog = dialogs.peek();
            if (dialog == null) {
                dialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
                dialog.show();

                dialogs.offer(dialog);
            }

            dialog.setContentText(content);
            dialog.setTitleText("未成功");
            dialog.setOnDismissListener(dismissListener);

            if (showRetry) {
                dialog.setConfirmText("重试");
                dialog.setCancelText("好的");
            }

            if (confirmClickListener != null) {
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
            }
            else {
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
            }

            dialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
    * 显示确认对话框
    * */
    public static void showConfirmDialog(Context context, String title, String content, SweetAlertDialog.OnSweetClickListener confirmClickListener) {
        showConfirmDialog(context, title, content, "好的", confirmClickListener);
    }

    public static void showConfirmDialog(Context context, String title, String content, String confirmTextString, SweetAlertDialog.OnSweetClickListener confirmClickListener) {
        dismissDialog(context);

        SweetAlertDialog dialog = new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE);
        dialog.setTitleText(title);
        dialog.setContentText(content);
        dialog.setConfirmText(confirmTextString);
        dialog.setCancelText("关闭");
        dialog.setOnDismissListener(dismissListener);
        dialog.setConfirmClickListener(confirmClickListener);
        dialog.show();

        dialogs.offer(dialog);
    }


    public static void dismissDialog(Context context) {
        dismissDialog(context, false);
    }

    public static void dismissDialog(Context context, boolean animation) {

        SweetAlertDialog dialog = dialogs.peek();
        if (dialog != null) {
            if (animation) {
                dialog.dismissWithAnimation();
            } else {
                dialog.dismiss();
            }
        }
    }

    private static DialogInterface.OnDismissListener dismissListener = new DialogInterface.OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialog) {
            dialogs.poll();
        }
    };
}
