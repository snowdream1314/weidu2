package com.caibo.weidu.bean;

import java.io.Serializable;

/**
 * Created by CB-Ye on 2016/6/16.
 */
public class More implements Serializable {
    private String title;
    private int imageId;
    private int arrowId;

    public More(String title, int imageId, int arrowId) {
        this.title = title;
        this.imageId = imageId;
        this.arrowId = arrowId;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public int getImageId() { return imageId; }

    public void setImageId(int ImageId) { this.imageId = imageId; }

    public int getArrowId() { return arrowId; }

    public void setArrowId(int arrowId) { this.arrowId = arrowId; }

}
