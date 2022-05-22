package com.anderson.apigithub_mvvm

import android.app.Application
import com.anderson.apigithub_mvvm.di.converterModule
import com.anderson.apigithub_mvvm.di.networkModule
import com.anderson.apigithub_mvvm.di.repositoryModule
import com.anderson.apigithub_mvvm.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by anderson on 21/09/19.
 */
class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext( this@AppApplication)
            modules(listOf(networkModule, converterModule, repositoryModule, viewModelModule))
        }
    }
}
