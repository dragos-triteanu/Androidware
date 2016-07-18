package com.smth.androidware.model;

/**
 * Created by Dragos on 7/17/2016.
 */
public class ViewPagerModel {

    private int imageId;

    private String title;

    public ViewPagerModel(int imageId, String title) {
        this.imageId = imageId;
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
