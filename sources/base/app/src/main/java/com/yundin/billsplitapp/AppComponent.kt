package com.yundin.billsplitapp

import android.app.Application
import com.yundin.core.ApplicationProvider
import com.yundin.core.scope.AppScope
import dagger.Component

@[AppScope Component(modules = [], dependencies = [Application::class])]
interface AppComponent : ApplicationProvider
