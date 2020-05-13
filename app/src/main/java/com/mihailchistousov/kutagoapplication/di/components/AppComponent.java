package com.mihailchistousov.kutagoapplication.di.components;

import android.content.Context;

import com.mihailchistousov.kutagoapplication.di.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by Mihail Chistousov on 13,Май,2020
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    Retrofit exposeRetrofit();
    Context exposeContext();
}
