package com.kanaya.common.base.application

import android.app.Application
import com.kanaya.common.di.appModule
import com.kanaya.common.di.repositoryModule
import com.kanaya.common.di.retrofitModule
import com.kanaya.common.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            modules(listOf(appModule, retrofitModule, repositoryModule, viewModelModule))
        }
    }
}