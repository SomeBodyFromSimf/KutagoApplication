package com.mihailchistousov.kutagoapplication.ui.main;

import android.os.Bundle;

import com.mihailchistousov.kutagoapplication.R;
import com.mihailchistousov.kutagoapplication.base.BaseActivity;
import com.mihailchistousov.kutagoapplication.di.components.DaggerFilmComponent;
import com.mihailchistousov.kutagoapplication.di.modules.FilmModule;
import com.mihailchistousov.kutagoapplication.mvp.model.ResponsePage;
import com.mihailchistousov.kutagoapplication.mvp.presenter.FilmPresenter;
import com.mihailchistousov.kutagoapplication.mvp.view.MainView;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainView {

    int pagesOnScreen = 0;

    @Inject protected FilmPresenter presenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        super.onViewReady(savedInstanceState);
        presenter.getFilms();
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerFilmComponent.builder()
                .appComponent(getAppComponent())
                .filmModule(new FilmModule(this))
                .build().inject(this);
    }

    @Override
    public void startLoading() {
        visibilityView(progressBar,true);
    }

    @Override
    public void onFilmsError(String message) {
        visibilityView(progressBar,false);
        ToastMessage(message);
    }

    @Override
    public void onFilmsLoaded(ResponsePage films) {


    }
}
