package com.caibo.weidu.util;

import android.content.Context;
import android.os.AsyncTask;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by snow on 2016/6/4.
 */
public class DataUtil {

    public interface NetReceiveDelete {
        public void receiveFail(DataUtil dataUtil, String message);
        public void receiveSuccess(DataUtil dataUtil, String result);
    }


    private NetReceiveDelete delegate;
    private Context mContext;
    private static final int GET = 1;
    private static final int POST = 2;

    public DataUtil(Context context) {
        this.mContext = context;
    }

    public void setDelegate(NetReceiveDelete delegate) {
        this.delegate = delegate;
    }

    public NetReceiveDelete getDelegate() { return delegate; }

    public void GETRequest(String url, Map<String, String> params) {
        connect(url, null, GET);
    }

    private void connect(final String url, final Map<String, String> params, final int request_type) {
        new AsyncTask<String, Void, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String...param) {

                OkHttpClient client = new OkHttpClient();

                Request request = null;
                if (request_type == GET) {
                    request = new Request.Builder()
                            .url(url)
                            .build();
                } else if (request_type == POST) {
                    MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
                    bodyBuilder.setType(MultipartBody.FORM);
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        bodyBuilder.addFormDataPart(entry.getKey(), entry.getValue());
                    }

                    RequestBody formBody = bodyBuilder.build();
                    request = new Request.Builder()
                            .url(url)
                            .post(formBody)
                            .build();
                } else {
                    return null;
                }


                Response response = null;
                DES des = new DES();
                String result = null;
                try {
                    response = client.newCall(request).execute();
                    if (response != null && response.isSuccessful()) {
                        result = des.decrypt(response.body().string());
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
