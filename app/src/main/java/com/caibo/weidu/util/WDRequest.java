package com.caibo.weidu.util;

import android.content.Context;
import android.util.Log;

import com.caibo.weidu.constant.WDConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by snow on 2016/6/4.
 */
public class WDRequest implements DataUtil.NetReceiveDelete{

    public enum Req_Tag{
        Tag_Register,

        Tag_Account_Categorys,
        Tag_Category_Accounts,
        Tag_Account_Detail,

        Tag_Favorite,
        Tag_Favorite_Remove,
        Tag_Favorite_List;
    }

    public interface WDRequestDelegate {
        public void requestFail(WDRequest req, String msg);
        public void requestSuccess(WDRequest req, String result);
    }

    private Context mContext;
    private WDRequestDelegate delegate;
    public Req_Tag tag;

    public WDRequest(Context context) {
        this.mContext = context;
    }

    public WDRequestDelegate getDelegate() { return delegate; }

    public void setDelegate(WDRequestDelegate delegate) { this.delegate = delegate; }


    /************************ method *******************************/
    public void register(String deviceId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("deviceId",deviceId);
        request("UserAuth/registerUser", params, Req_Tag.Tag_Register);
    }

    public void account_category() {
        request("AccountCategory/cats", null, Req_Tag.Tag_Account_Categorys);
    }

    public void category_accounts(String ac_id, int page) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("ac_id", ac_id);
        params.put("p", page+"");
        request("Account/accounts", params, Req_Tag.Tag_Category_Accounts);
    }

    public void account_detail(String accountId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("a_id", accountId);
        params.put("format", "clientdetailview_v1");
        request("Account/accountDetail", params, Req_Tag.Tag_Account_Detail);
    }

    public void favorite(String accountId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("a_id", accountId);
        request("AccountFavorite/addFavorite", params, Req_Tag.Tag_Favorite);
    }

    public void favorite_remove(String accountId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("a_id", accountId);
        request("AccountFavorite/removeFav", params, Req_Tag.Tag_Favorite_Remove);
    }

    public void favorite_list(int page) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("p", page+"");
        request("AccountFavorite/favList", params, Req_Tag.Tag_Favorite_List);
    }


    /********************************** deal with params *****************************************/
    private String appendParams(String method, Map<String, String> params)  {
        StringBuilder sb = new StringBuilder(WDConstant.URL);

        if (method.equals("AccountCategory/cats")) {
            return sb.append(method).toString();
        }

        sb.append(method).append("?");

        params.put("appcode", WDConstant.APP_CODE);
        params.put("v", WDConstant.VERSION);
        params.put("devicetype", WDConstant.DEVICE_TYPE);

        if (UserUtil.isRegistered(mContext)) {
            params.put("wxhsession", UserUtil.getSession(mContext));
        }

        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }

        sb.deleteCharAt(sb.length() - 1);
        Log.i("request_url", sb.toString());
        return sb.toString();
    }

    private void request(String method, Map<String, String> params, Req_Tag req_tag) {
        tag = req_tag;

        DataUtil dataUtil = new DataUtil(mContext);
        dataUtil.setDelegate(this);
        dataUtil.GETRequest(appendParams(method, params), params);
    }

    @Override
    public void receiveSuccess(DataUtil dataUtil, String result) {
        Log.i("receiveSuccess", result);
        delegate.requestSuccess(this, result);
    }

    @Override
    public void receiveFail(DataUtil dataUtil, String message) {
        Log.i("receiveFail", message);
        delegate.requestFail(this, message);
    }

}
