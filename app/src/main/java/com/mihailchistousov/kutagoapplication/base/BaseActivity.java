package com.mihailchistousov.kutagoapplication.base;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.CallSuper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mihailchistousov.kutagoapplication.R;
import com.mihailchistousov.kutagoapplication.di.components.AppComponent;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mihail Chistousov on 13,Май,2020
 */
public abstract class  BaseActivity extends AppCompatActivity {

    @BindView(R.id.swipe_container)
    protected SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.search)
    protected TextView searchView;

    @BindView(R.id.recyclerview)
    protected RecyclerView recyclerView;

    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;

    @BindView(R.id.not_found_container)
    protected LinearLayout notFoundContainer;

    @BindView(R.id.not_found_text)
    protected TextView notFoundTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
        onViewReady(savedInstanceState);
    }

    protected abstract int getContentView();

    @CallSuper
    protected void onViewReady(Bundle savedInstanceState) {
        resolveDaggerDependency();
    }

    protected abstract void resolveDaggerDependency();

    protected AppComponent getAppComponent() {
        return ((BaseApp) getApplication()).getAppComponent();
    }

    public void showNotFoundLayout(String text){
        notFoundTextView.setText(getString(R.string.not_found_first,text));
        notFoundContainer.setVisibility(View.VISIBLE);
    }
    public void hideNotFoundLayout(){
        notFoundContainer.setVisibility(View.GONE);
    }

    public void visibilityView(View view, boolean status) {
        view.setVisibility((status)? View.VISIBLE:View.GONE);
    }

    public void ToastMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

}
