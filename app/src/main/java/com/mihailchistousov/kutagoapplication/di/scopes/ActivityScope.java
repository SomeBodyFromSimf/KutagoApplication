package com.mihailchistousov.kutagoapplication.di.scopes;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

import javax.inject.Scope;


/**
 * Created by Mihail Chistousov on 13,Май,2020
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {
}
