package com.caibo.weidu.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by snow on 2016/6/6.
 */
public class MySharedPreference {

    private SharedPreferences mSharedPreference;

    public MySharedPreference(Context context) {
        mSharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getKeyStr(String key) { return mSharedPreference.getString(key, ""); }

    public boolean setKeyStr(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreference.edit();
        editor.putString(key, value);
        editor.commit();
        return true;
    }

    public int getKeyInt(String key) { return  mSharedPreference.getInt(key, 0); }

    public boolean setKeyInt(String key, int value) {
        SharedPreferences.Editor editor = mSharedPreference.edit();
        editor.putInt(key, value);
        editor.commit();
        return true;
    }

    public boolean getKeyBoolean(String key) { return mSharedPreference.getBoolean(key, false); }

    public boolean setKeyBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = mSharedPreference.edit();
        editor.putBoolean(key,value);
        editor.commit();
        return true;
    }

    public void removeKey(String key) {
        SharedPreferences.Editor editor = mSharedPreference.edit();
        editor.remove(key);
        editor.commit();
    }
}
