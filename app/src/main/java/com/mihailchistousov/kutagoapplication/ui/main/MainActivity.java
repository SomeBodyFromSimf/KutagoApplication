package com.mihailchistousov.kutagoapplication.ui.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.mihailchistousov.kutagoapplication.base.BaseActivity;
import com.mihailchistousov.kutagoapplication.di.components.DaggerFilmComponent;
import com.mihailchistousov.kutagoapplication.di.modules.FilmModule;
import com.mihailchistousov.kutagoapplication.mvp.model.ResponsePage;
import com.mihailchistousov.kutagoapplication.mvp.presenter.FilmPresenter;
import com.mihailchistousov.kutagoapplication.ui.main.adapter.Adapter;
import com.mihailchistousov.kutagoapplication.ui.main.adapter.Decoration;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends BaseActivity  {

    int pagesOnScreen = 0;

    @Inject protected FilmPresenter presenter;
    private Adapter adapter;

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        super.onViewReady(savedInstanceState);
        initList();
        getFilms();
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerFilmComponent.builder()
                .appComponent(getAppComponent())
                .filmModule(new FilmModule(this))
                .build().inject(this);
    }
    @Override
    protected void onRefresh() {
        adapter.clearList();
        pagesOnScreen = 0;
        searchView.setQuery("", false);
        searchView.clearFocus();
        getFilms();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void filterListBySearchView(String title) {
        if(title.isEmpty()) {
            Completable.fromAction(this::hideNotFoundLayout).subscribeOn(AndroidSchedulers.mainThread()).subscribe();
            adapter.returnAll();
            return;
        }
        adapter.filterListByTitle(title);
    }

    public void getFilms() {
            presenter.getFilms(pagesOnScreen+1);
    }

    private void initList() {
        int spanCount = (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)? 2:3;
        recyclerView.setLayoutManager(new GridLayoutManager(this,spanCount));
        adapter = new Adapter(this,this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new Decoration(spanCount,10));
    }

    @Override
    public void startLoading() {
        if(pagesOnScreen==0)
            visibilityView(progressBar,true);

    }

    @Override
    public void onFilmsError(String message) {
        if(pagesOnScreen == 0) {
            visibilityView(progressBar,false);
            showNotFoundLayout("");
        }
        else
            adapter.changeProgressItem();
        snackBarMessage(message);
    }

    @Override
    public void onFilmsLoaded(ResponsePage responsePage) {
        if(pagesOnScreen==0) {
            visibilityView(progressBar,false);
            if(notFoundContainer.getVisibility() == View.VISIBLE) hideNotFoundLayout();
        }
        pagesOnScreen++;
        adapter.addFilms(responsePage.getFilms());
    }
}
