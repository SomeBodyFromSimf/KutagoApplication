package com.mihailchistousov.kutagoapplication.mvp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.mihailchistousov.kutagoapplication.API.KudagoAPI;
import com.mihailchistousov.kutagoapplication.R;
import com.mihailchistousov.kutagoapplication.Utils.Resource;
import com.mihailchistousov.kutagoapplication.base.BasePresenter;
import com.mihailchistousov.kutagoapplication.mvp.model.ResponsePage;
import com.mihailchistousov.kutagoapplication.mvp.view.MainView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Mihail Chistousov on 13,Май,2020
 */

public class FilmPresenter extends BasePresenter<MainView> {

    @Inject protected KudagoAPI api;
    @Inject protected Context context;

    @Inject
    public FilmPresenter() {}

    @SuppressLint("CheckResult")
    public void getFilms(int current_page) {
        if(!isNetworkAvailable()) {
            getView().onFilmsError(context.getResources().getString(R.string.error_load_films));
            startTimerForNetworkAvailable();
            return;
        }
        getView().startLoading();
        api.getFilms(current_page)
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

    private void startTimerForNetworkAvailable() {
        CompositeDisposable composite = new CompositeDisposable();

        composite.add(Observable.timer(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .repeat()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (aLong) -> {
                            if(isNetworkAvailable()) {
                                composite.dispose();
                                getView().snackBarMessage(context.getString(R.string.net_available));
                                getView().getFilms();

                            }
                        }
                ));
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivity =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            for (NetworkInfo networkInfo : info) {
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }
}
