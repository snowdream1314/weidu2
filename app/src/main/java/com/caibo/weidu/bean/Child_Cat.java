package com.caibo.weidu.bean;

import java.io.Serializable;

/**
 * Created by snow on 2016/6/10.
 */
public class Child_Cat implements Serializable {

    protected String ac_id;
    protected String ac_name;
    protected String ac_seq;
    protected String ac_is_parent;
    protected String ac_parent_id;
    protected String ac_need_geo;
    protected String ac_min_version;
    protected String ac_has_more;
    protected String ac_index_item_count;

    public String getAc_id() { return ac_id; }

    public void setAc_id(String ac_id) { this.ac_id = ac_id; }

    public String getAc_name() { return ac_name; }

    public void setAc_name(String ac_name) { this.ac_name = ac_name; }

    public String getAc_seq() { return ac_seq; }

    public void setAc_seq(String ac_seq) { this.ac_seq = ac_seq; }

    public String getAc_is_parent() { return ac_is_parent; }

    public void setAc_is_parent(String ac_is_parent) { this.ac_is_parent = ac_is_parent; }

    public String getAc_parent_id() { return ac_parent_id; }

    public void setAc_parent_id(String ac_parent_id) { this.ac_parent_id = ac_parent_id; }

    public String getAc_need_geo() { return ac_need_geo; }

    public void setAc_need_geo(String ac_need_geo) { this.ac_need_geo = ac_need_geo; }

    public String getAc_min_version() { return ac_min_version; }

    public void setAc_min_version(String ac_min_version) { this.ac_min_version = ac_min_version; }

    public String getAc_has_more() { return ac_has_more; }

    public void setAc_has_more(String ac_has_more) { this.ac_has_more = ac_has_more; }

    public String getAc_index_item_count() { return ac_index_item_count; }

    public void setAc_index_item_count(String ac_index_item_count) { this.ac_index_item_count = ac_index_item_count; }

}
