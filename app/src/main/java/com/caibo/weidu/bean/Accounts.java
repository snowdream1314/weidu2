package com.caibo.weidu.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by snow on 2016/6/10.
 */
public class Accounts extends Child_Cat implements Serializable {
    private List<Child_Cat> childCats;
    private List<Recommend_Account> recommendAccount;

    public List<Child_Cat> getChildCats() { return childCats; }

    public void setChildCats(List<Child_Cat> childCats) { this.childCats = childCats; }

    public List<Recommend_Account> getRecommendAccount() { return recommendAccount; }

    public void setRecommendAccount(List<Recommend_Account> recommendAccount) { this.recommendAccount = recommendAccount; }

}
