package com.mihailchistousov.kutagoapplication.base;

import android.app.Application;

import com.mihailchistousov.kutagoapplication.di.components.AppComponent;
import com.mihailchistousov.kutagoapplication.di.components.DaggerAppComponent;

/**
 * Created by Mihail Chistousov on 13,Май,2020
 */
public class BaseApp extends Application {

    private AppComponent component;
    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
    }

    private void initAppComponent() {
        component = DaggerAppComponent.builder().build();
    }

    public AppComponent getAppComponent() {
        return component;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
