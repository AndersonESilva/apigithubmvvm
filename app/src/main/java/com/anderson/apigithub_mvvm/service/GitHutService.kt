package com.anderson.apigithub_mvvm.service

import com.anderson.apigithub_mvvm.data.response.ItemResponse
import com.anderson.apigithub_mvvm.data.response.PullRequestResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by anderson on 21/09/19.
 */
interface GitHutService {

    @GET("search/repositories")
    fun getListRepository(
        @Query("q") q: String,
        @Query("sort") sort: String,
        @Query("page")  page: Int
    ): Call<ItemResponse>

    @GET("repos/{creator}/{repository}/pulls")
    fun getPullsRequest(
        @Path("creator") creator: String,
        @Path("repository") repository: String
    ): Call<List<PullRequestResponse>>
}