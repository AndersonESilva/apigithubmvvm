package com.anderson.apigithub_mvvm.di

import android.app.Application
import android.content.Context
import com.anderson.apigithub_mvvm.AppApplication
import com.anderson.apigithub_mvvm.R
import com.anderson.apigithub_mvvm.service.GitHubService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by anderson on 21/09/19.
 */
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(app: Application): Context {
        return app.applicationContext
    }

    @Singleton
    @Provides
    fun provideGitHubService(): GitHubService {
        return Retrofit.Builder()
            .baseUrl(AppApplication.instance.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
            .create(GitHubService::class.java)
    }

}
