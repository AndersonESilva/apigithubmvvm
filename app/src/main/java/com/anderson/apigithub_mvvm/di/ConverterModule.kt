package com.anderson.apigithub_mvvm.di

import com.anderson.apigithub_mvvm.feature.main.conveter.MainConverter
import com.anderson.apigithub_mvvm.feature.pullRequest.converter.PullRequestConverter
import org.koin.dsl.module

/**
 * Created by anderson on 22/05/2022.
 */
val converterModule = module {
    single { provideMainConverter() }
    single { providePullRequestConverter() }
}

private fun provideMainConverter() = MainConverter()

private fun providePullRequestConverter() = PullRequestConverter()
