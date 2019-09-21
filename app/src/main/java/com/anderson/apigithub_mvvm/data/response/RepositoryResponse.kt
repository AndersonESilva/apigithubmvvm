package com.anderson.apigithub_mvvm.data.response

import com.google.gson.annotations.SerializedName

data class RepositoryResponse (
    val name: String,
    @SerializedName("owner.avatar_url")
    val imgUrl: String,
    @SerializedName("stargazers_count")
    val starsCount: Int,
    @SerializedName("forks_count")
    val forksCount: Int,
    @SerializedName("pulls_url")
    val pullsUrls: String
)