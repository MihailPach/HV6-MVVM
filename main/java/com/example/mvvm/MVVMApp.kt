package com.example.mvvm

import android.app.Application
import com.example.mvvm.koin.databaseModule
import com.example.mvvm.koin.networkModule
import com.example.mvvm.koin.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin


class MVVMApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MVVMApp)
            modules(
                databaseModule,
                networkModule,
                repositoryModule
            )
        }
    }
}