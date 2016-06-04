package com.caibo.weidu.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.view.ContextThemeWrapper;

/**
 * Created by snow on 2016/6/4.
 */
public class UserDataUtil {

    private static Context mContext;
    private static SharedPreferences pref;
    private static String deviceId;

    public UserDataUtil(Context context) {
        this.mContext = context;
        this.pref = mContext.getSharedPreferences("register",Context.MODE_PRIVATE);
    }

    public static void getAndSaveDeviceId() {
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        deviceId = tm.getDeviceId();
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("deviceId", deviceId);
        editor.commit();
    }

    public static String getDeviceId() {
        return  deviceId;
    }

    public static void saveSession(String session) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("session", session);
        editor.commit();
    }

    public static boolean isRegistered() {
        return pref.getString("session","").isEmpty() ? false : true;
    }

}
