package com.mihailchistousov.kutagoapplication.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.mihailchistousov.kutagoapplication.mvp.model.ResponsePage;

/**
 * Created by Mihail Chistousov on 13,Май,2020

 */
//@StateStrategyType(SingleStateStrategy.class)
public interface MainView extends MvpView {
    void startLoading();

    void onFilmsError(String message);

    void onFilmsLoaded(ResponsePage films);

    void getFilms();

    void snackBarMessage(String message);

    void showNotFoundLayout(String text);

    void hideNotFoundLayout();
}
