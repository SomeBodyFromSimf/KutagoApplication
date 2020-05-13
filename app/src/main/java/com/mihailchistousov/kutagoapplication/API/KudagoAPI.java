package com.mihailchistousov.kutagoapplication.API;

import com.mihailchistousov.kutagoapplication.mvp.model.ResponsePage;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Mihail Chistousov on 13,Май,2020
 */
public interface KudagoAPI {
    @GET("/public-api/v1.4/movies/")
    Flowable<ResponsePage> getFilms(@Query("page") Integer page);
}
