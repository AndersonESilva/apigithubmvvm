package com.anderson.apigithub_mvvm.di

import com.anderson.apigithub_mvvm.service.GitHubRepository
import com.anderson.apigithub_mvvm.service.GitHubService
import org.koin.dsl.module

/**
 * Created by anderson on 22/05/2022.
 */
val repositoryModule = module {
    single { provideGitHubRepository(get()) }
}

private fun provideGitHubRepository(service: GitHubService) = GitHubRepository(service)
