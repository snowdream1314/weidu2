package com.caibo.weidu.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;

/**
 * Created by snow on 2016/6/4.
 */
public class UserUtil {

    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        MySharedPreference mp = new MySharedPreference(context);
        mp.setKeyStr("deviceId", tm.getDeviceId());
        return  deviceId;
    }

    public static void setSession(Context context, String session) {
        MySharedPreference mp = new MySharedPreference(context);
        mp.setKeyStr("session", session);
    }

    public static String getSession(Context context) {
        MySharedPreference mp = new MySharedPreference(context);
        return mp.getKeyStr("session");
    }

    public static boolean isRegistered(Context context) {
        MySharedPreference mp = new MySharedPreference(context);

        if (mp.getKeyStr("session").equals("")) {
            UserUtil.setSession(context, "");
            return false;
        }
        return mp.getKeyStr("session").isEmpty() ? false : true;
    }

}
