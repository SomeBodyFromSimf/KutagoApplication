package com.mihailchistousov.kutagoapplication.mvp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mihail Chistousov on 13,Май,2020
 */
public class Poster {
    @SerializedName("image")
    @Expose
    private String image_url;

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
