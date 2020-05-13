package com.mihailchistousov.kutagoapplication.di.modules;

import com.mihailchistousov.kutagoapplication.API.KudagoAPI;
import com.mihailchistousov.kutagoapplication.di.scopes.ActivityScope;
import com.mihailchistousov.kutagoapplication.mvp.view.MainView;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Mihail Chistousov on 13,Май,2020
 */
@Module
public class FilmModule {

    private MainView view;

    public FilmModule(MainView view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    KudagoAPI provideApi(Retrofit retrofit) {
        return retrofit.create(KudagoAPI.class);
    }

    @ActivityScope
    @Provides
    MainView provideView(){
        return view;
    }

}
