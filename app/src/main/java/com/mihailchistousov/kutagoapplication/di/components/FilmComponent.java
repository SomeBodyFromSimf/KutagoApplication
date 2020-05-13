package com.mihailchistousov.kutagoapplication.di.components;

import com.mihailchistousov.kutagoapplication.di.modules.FilmModule;
import com.mihailchistousov.kutagoapplication.di.scopes.ActivityScope;
import com.mihailchistousov.kutagoapplication.ui.main.MainActivity;

import dagger.Component;

/**
 * Created by Mihail Chistousov on 13,Май,2020
 */
@ActivityScope
@Component(modules = FilmModule.class,dependencies = AppComponent.class)
public interface FilmComponent {
    void inject(MainActivity activity);
}
