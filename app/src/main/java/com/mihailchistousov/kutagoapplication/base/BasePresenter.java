package com.mihailchistousov.kutagoapplication.base;

import com.mihailchistousov.kutagoapplication.mvp.view.BaseView;

import javax.inject.Inject;


/**
 * Created by Mihail Chistousov on 13,Май,2020
 */
public class BasePresenter<V extends BaseView> {

    @Inject protected V view;
    protected V getView() {
        return view;
    }

}
