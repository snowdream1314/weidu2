package com.caibo.weidu.util;

import android.os.AsyncTask;

import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by snow on 2016/6/4.
 */
public class DataUtil {

    private WDDelegate delegate;

    public void setDelegate(WDDelegate delegate) {
        this.delegate = delegate;
    }

    public void request(String url, Map<String, String> params, WDRequest.rq_tag tag) {
        connect(url, params, tag);
    }

    private void connect(final String url, final Map<String, String> params, final WDRequest.rq_tag tag) {
        new AsyncTask<String, Void, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String...param) {

                OkHttpClient client = new OkHttpClient();

                Request request = null;
                request = new Request.Builder()
                        .url(url)
                        .build();

                Response response = null;
                String result = null;
                try {
                    response = client.newCall(request).execute();
                    if (response != null && response.isSuccessful()) {
                        result = response.body().string();
                    } else {
                        return "0";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;

            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                if ("0".equals(result)) {
                    if (delegate !=null) {
                        delegate.receiveFail(DataUtil.this, "网络连接失败, 请稍后重试");
                    }
                } else if (result == null) {
                    if (delegate != null) {
                        delegate.receiveFail(DataUtil.this, "数据请求有误，请重试");
                    }
                } else {
                    if (delegate != null) {
                        delegate.receiveSuccess(DataUtil.this, result);
                    }
                }
            }
        }.execute();
    }
}
