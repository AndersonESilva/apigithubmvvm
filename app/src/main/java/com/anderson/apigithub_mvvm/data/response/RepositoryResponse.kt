package com.anderson.apigithub_mvvm.data.response

import com.google.gson.annotations.SerializedName

data class RepositoryResponse (
    @SerializedName("node_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("stargazers_count")
    val starsCount: Int,
    @SerializedName("forks_count")
    val forksCount: Int,
    @SerializedName("pulls_url")
    val pullsUrls: String,
    @SerializedName("owner")
    val ownerResponse: OwnerResponse
)