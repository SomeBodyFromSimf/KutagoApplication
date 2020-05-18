package com.mihailchistousov.kutagoapplication.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.CallSuper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.rxbinding3.widget.RxSearchView;
import com.mihailchistousov.kutagoapplication.R;
import com.mihailchistousov.kutagoapplication.di.components.AppComponent;
import com.mihailchistousov.kutagoapplication.mvp.view.MainView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Mihail Chistousov on 13,Май,2020
 */
public abstract class  BaseActivity extends MvpAppCompatActivity implements MainView {

    @BindView(R.id.swipe_container)
    protected SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.search)
    protected SearchView searchView;

    @BindView(R.id.recyclerview)
    protected RecyclerView recyclerView;

    @BindView(R.id.not_found_container)
    protected LinearLayout notFoundContainer;

    @BindView(R.id.not_found_text)
    protected TextView notFoundTextView;

    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;

    Disposable subscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        onViewReady(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscribe.dispose();
    }

    @SuppressLint("CheckResult")
    @CallSuper
    protected void onViewReady(Bundle savedInstanceState) {
        resolveDaggerDependency();
        subscribe = RxSearchView.queryTextChanges(searchView)
                .skipInitialValue()
                .debounce(1, TimeUnit.SECONDS)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .map(CharSequence::toString)
                .subscribe(this::filterListBySearchView);
        swipeRefreshLayout.setOnRefreshListener(this::onRefresh);
        searchView.setOnCloseListener(()->{
            snackBarMessage("close");
            return true;
        });
    }

    protected abstract void onRefresh();

    protected abstract void filterListBySearchView(String string);

    protected abstract void resolveDaggerDependency();


    protected AppComponent getAppComponent() {
        return ((BaseApp) getApplication()).getAppComponent();
    }

    @Override
    public void showNotFoundLayout(String text){
        notFoundTextView.setText((!text.isEmpty())?getString(R.string.not_found_first,text):getString(R.string.not_found));
        notFoundContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNotFoundLayout(){
        notFoundContainer.setVisibility(View.GONE);
    }

    @Override
    public void snackBarMessage(String message) {
        Snackbar.make(swipeRefreshLayout, message, Snackbar.LENGTH_LONG).show();
    }

    public void visibilityView(View view, boolean status) {
        view.setVisibility((status)? View.VISIBLE:View.GONE);
    }

}
