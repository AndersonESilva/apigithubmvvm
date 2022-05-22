package com.anderson.apigithub_mvvm.di

import com.anderson.apigithub_mvvm.feature.main.conveter.MainConverter
import com.anderson.apigithub_mvvm.feature.main.viewmodel.MainViewModel
import com.anderson.apigithub_mvvm.feature.pullRequest.converter.PullRequestConverter
import com.anderson.apigithub_mvvm.feature.pullRequest.viewmodel.PullRequestViewModel
import com.anderson.apigithub_mvvm.service.GitHubRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by anderson on 22/05/2022.
 */
val viewModelModule = module {
    viewModel { provideMainViewModel(get(), get()) }
    viewModel { providePullRequestViewModel(get(), get()) }
}

private fun provideMainViewModel(repository: GitHubRepository, converter: MainConverter) = MainViewModel(repository, converter)

private fun providePullRequestViewModel(repository: GitHubRepository, converter: PullRequestConverter) = PullRequestViewModel(repository, converter)
