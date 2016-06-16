package com.caibo.weidu.bean;

import java.io.Serializable;

/**
 * Created by snow on 2016/6/10.
 */
public class Account implements Serializable{
    protected String a_id;
    protected String a_logo;
    protected String a_wx_no;
    protected String a_name;
    protected String a_is_valided;
    protected String ufa_create_time;
    protected String a_desc;
    protected String a_valid_reason;
    protected String a_rank;


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

    public String getUfa_create_time() { return ufa_create_time; }

    public void setUfa_create_time(String ufa_create_time) { this.ufa_create_time = ufa_create_time; }

    public String getA_desc() { return a_desc; }

    public void setA_desc(String a_desc) { this.a_desc = a_desc; }

    public String getA_valid_reason() { return a_valid_reason; }

    public void setA_valid_reason(String a_valid_reason) { this.a_valid_reason = a_valid_reason; }

    public String getA_rank() { return a_rank; }

    public void setA_rank(String a_rank) { this.a_rank = a_rank; }
}
