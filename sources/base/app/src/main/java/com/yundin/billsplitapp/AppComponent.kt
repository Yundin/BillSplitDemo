package com.yundin.billsplitapp

import android.app.Application
import com.yundin.core.ApplicationProvider
import com.yundin.core.scope.AppScope
import com.yundin.datasource.di.GroupRepositoryModule
import dagger.Component

@[AppScope Component(
    modules = [
        GroupRepositoryModule::class
    ],
    dependencies = [Application::class]
)]
interface AppComponent : ApplicationProvider
