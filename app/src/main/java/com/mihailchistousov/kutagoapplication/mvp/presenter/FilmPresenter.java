package com.mihailchistousov.kutagoapplication.mvp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.mihailchistousov.kutagoapplication.API.KudagoAPI;
import com.mihailchistousov.kutagoapplication.R;
import com.mihailchistousov.kutagoapplication.Utils.Resource;
import com.mihailchistousov.kutagoapplication.base.BasePresenter;
import com.mihailchistousov.kutagoapplication.mvp.model.ResponsePage;
import com.mihailchistousov.kutagoapplication.mvp.view.MainView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Mihail Chistousov on 13,Май,2020
 */
public class FilmPresenter extends BasePresenter<MainView> {




    @Inject protected KudagoAPI api;
    @Inject protected Context context;
    @Inject
    public FilmPresenter() {
    }

    @SuppressLint("CheckResult")
    public void getFilms() {
        getView().startLoading();
        api.getFilms(1)
                .subscribeOn(Schedulers.io())
                .onErrorReturn(throwable -> {
                    ResponsePage page = new ResponsePage();
                    page.setCount(-1);
                    return page;
                })
                .map(page -> {
                    if(page.getCount() == -1) {
                        return Resource.error(context.getResources().getString(R.string.error_load_films),null);
                    }
                    return Resource.success(page);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resource -> {
                    if(resource.data != null)
                        getView().onFilmsLoaded(((ResponsePage) resource.data));
                    else getView().onFilmsError(resource.message);
                });
    }
}
