package com.mihailchistousov.kutagoapplication.base;

import com.mihailchistousov.kutagoapplication.mvp.view.BaseView;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Mihail Chistousov on 13,Май,2020
 */
public class BasePresenter<V extends BaseView> {

    @Inject protected V view;
    protected V getView() {
        return view;
    }

    protected <T> void subscibe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
