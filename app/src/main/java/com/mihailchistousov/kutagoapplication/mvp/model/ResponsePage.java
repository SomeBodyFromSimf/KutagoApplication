package com.mihailchistousov.kutagoapplication.mvp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mihail Chistousov on 13,Май,2020
 */
public class ResponsePage {
    @SerializedName("count")
    @Expose
    private Integer count;

    @SerializedName("results")
    @Expose
    private List<Film> films;


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }


}
