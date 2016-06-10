package com.caibo.weidu.bean;

import java.io.Serializable;

/**
 * Created by snow on 2016/6/10.
 */
public class Recommend_Account implements Serializable{
    private String ar_seq;
    private String ar_badge;
    private String ar_id;
    private String a_id;
    private String a_logo;
    private String a_wx_no;
    private String a_name;
    private String a_is_valided;

    public String getAr_seq() { return ar_seq; }

    public void setAr_seq(String ar_seq) { this.ar_seq = ar_seq; }

    public String getAr_badge() { return this.ar_badge; }

    public void setAr_badge(String ar_badge) { this.ar_badge = ar_badge; }

    public String getAr_id() { return ar_id; }

    public void setAr_id(String ar_id) { this.ar_id = ar_id; }

    public String getA_id() { return a_id; }

    public void setA_id(String a_id) { this.a_id = a_id; }

    public String getA_logo() { return a_logo; }

    public void setA_logo(String a_logo) { this.a_logo = a_logo; }

    public String getA_wx_no() { return a_wx_no; }

    public void setA_wx_no(String a_wx_no) { this.a_wx_no = a_wx_no; }

    public String getA_name() { return a_name; }

    public void setA_name(String a_name) { this.a_name = a_name; }

    public String getA_is_valided() { return a_is_valided; }

    public void setA_is_valided(String a_is_valided) { this.a_is_valided = a_is_valided; }
}
