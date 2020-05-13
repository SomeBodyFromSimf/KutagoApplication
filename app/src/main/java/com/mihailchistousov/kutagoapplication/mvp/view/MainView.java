package com.mihailchistousov.kutagoapplication.mvp.view;

import com.mihailchistousov.kutagoapplication.mvp.model.ResponsePage;

/**
 * Created by Mihail Chistousov on 13,Май,2020
 */
public interface MainView extends BaseView{
    void startLoading();

    void onFilmsError(String message);

    void onFilmsLoaded(ResponsePage films);
}
