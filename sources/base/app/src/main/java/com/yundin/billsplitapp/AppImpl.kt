package com.yundin.billsplitapp

import android.app.Application
import com.yundin.billsplitapp.di.AppComponent
import com.yundin.billsplitapp.di.DaggerAppComponent
import com.yundin.core.App
import com.yundin.core.ApplicationProvider

class AppImpl : Application(), App {

    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    override fun getAppProvider(): ApplicationProvider = appComponent
}
