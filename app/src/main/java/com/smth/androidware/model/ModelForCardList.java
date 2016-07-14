package com.smth.androidware.model;

/**
 * Created by Dragos on 7/14/2016.
 */
public class ModelForCardList {

    private Integer imageId;
    private String title;
    private String description;

    public ModelForCardList(Integer imageId, String title, String description) {
        this.imageId = imageId;
        this.title = title;
        this.description = description;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
