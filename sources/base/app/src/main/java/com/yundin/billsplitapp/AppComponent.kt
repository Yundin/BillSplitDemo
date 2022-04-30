package com.yundin.billsplitapp

import android.app.Application
import com.yundin.core.ApplicationProvider
import com.yundin.core.scope.AppScope
import com.yundin.datasource.di.DataSourceModule
import dagger.Component

@[AppScope Component(
    modules = [
        DataSourceModule::class
    ],
    dependencies = [Application::class]
)]
interface AppComponent : ApplicationProvider
