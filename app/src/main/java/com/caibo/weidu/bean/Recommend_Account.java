package com.caibo.weidu.bean;

import java.io.Serializable;

/**
 * Created by snow on 2016/6/11.
 */
public class Recommend_Account extends Account implements Serializable {
    private String ar_seq;
    private String ar_badge;
    private String ar_id;

    public String getAr_seq() { return ar_seq; }

    public void setAr_seq(String ar_seq) { this.ar_seq = ar_seq; }

    public String getAr_badge() { return this.ar_badge; }

    public void setAr_badge(String ar_badge) { this.ar_badge = ar_badge; }

    public String getAr_id() { return ar_id; }

    public void setAr_id(String ar_id) { this.ar_id = ar_id; }

}
