package com.caibo.weidu.util;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by snow on 2016/6/4.
 */
public class WDRequest {

    public enum rq_tag{
        TAG_REGISTER,

        TAG_ACCOUNT_LIST,
        TAG_ACCOUNT_DETAIL,

        TAG_FAVORITE,
        TAG_FAVORITE_REMOVE,
        TAG_FAVORITE_LIST;
    }


    private DataUtil dataUtil;
    private Context mContext;

    public WDRequest(Context context) {
        this.mContext = context;
    }

    /************************ method *******************************/
    public void register(String deviceId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("deviceId",deviceId);
        dataUtil.request("UserAuth/registerUser", params, rq_tag.TAG_REGISTER);
    }

    public void account_category(String page) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("page", page);
    }

    public void account_detail(String accountId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("accountId", accountId);
    }

    public void favorite(String accountId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("accountId", accountId);
    }

    public void favorite_remove(String accountId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("accountId", accountId);
    }

    public void favorite_list(String page) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("page", page);
    }

}
