package com.anderson.apigithub_mvvm.service

import com.anderson.apigithub_mvvm.data.response.ItemResponse
import com.anderson.apigithub_mvvm.data.response.PullRequestResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by anderson on 21/09/19.
 */
interface GitHubService {

    @GET("search/repositories")
    suspend fun getRepositories(
        @Query("q") q: String,
        @Query("sort") sort: String,
        @Query("page")  page: Int
    ): Response<ItemResponse>

    @GET("repos/{creator}/{repositoryName}/pulls")
    fun getPullsRequest(
        @Path("creator") creator: String,
        @Path("repositoryName") repositoryName: String
    ): Call<List<PullRequestResponse>>

    @GET("repos/{creator}/{repositoryName}/pulls")
    suspend fun getPullRequestes(
        @Path("creator") creator: String,
        @Path("repositoryName") repositoryName: String
    ): Response<List<PullRequestResponse>>
}