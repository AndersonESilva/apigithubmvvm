package com.anderson.apigithub_mvvm.di

import com.anderson.apigithub_mvvm.BuildConfig
import com.anderson.apigithub_mvvm.BuildConfig.BASE_URL
import com.anderson.apigithub_mvvm.service.GitHubService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by anderson on 22/05/2022.
 */
val networkModule = module {
    single { provideLoggingInterceptor() }
    single { provideGitHubService(get()) }
}

private fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
    if (BuildConfig.DEBUG) {
        this.level = HttpLoggingInterceptor.Level.BODY
    } else {
        this.level = HttpLoggingInterceptor.Level.NONE
    }
}

private fun provideGitHubService(loggingInterceptor: HttpLoggingInterceptor): GitHubService {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(createClient(loggingInterceptor))
        .build()
        .create(GitHubService::class.java)
}

private fun createClient(loggingInterceptor: HttpLoggingInterceptor) = OkHttpClient.Builder().run{
    addInterceptor(loggingInterceptor)
    build()
}
