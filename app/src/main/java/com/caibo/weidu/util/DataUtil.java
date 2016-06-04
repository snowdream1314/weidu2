package com.caibo.weidu.util;

import android.os.AsyncTask;

/**
 * Created by snow on 2016/6/4.
 */
public class DataUtil {

    public void request() {
        connect();
    }

    private void connect() {
        new AsyncTask<String, Void, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String...param) {
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
            }
        }.execute();
    }
}
