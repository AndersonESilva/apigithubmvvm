package com.anderson.apigithub_mvvm.service

/**
 * Created by anderson on 15/11/2020.
 */
class GitHubRepository(val service: GitHubService) : BaseDataSource(){

    companion object {
        const val  q = "language:Java"
        const val  sort = "stars"
    }

    suspend fun getRepositories(page : Int) = getResult { service.getRepositories(q,sort,page) }

    suspend fun getPullRequestes(creator : String, repositoryName: String) = getResult { service.getPullRequestes(creator,repositoryName) }

}
