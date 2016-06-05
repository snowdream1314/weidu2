package com.caibo.weidu.util;

/**
 * Created by snow on 2016/6/4.
 */
public interface WDDelegate {
    public void receiveFail(DataUtil dataUtil, String msg);
    public void receiveSuccess(DataUtil dataUtil, String msg);
}
