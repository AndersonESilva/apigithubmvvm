package com.anderson.apigithub_mvvm.data.response

import com.google.gson.annotations.SerializedName

/**
 * Created by anderson on 22/09/19.
 */
data class PullRequestResponse(
    @SerializedName("node_id")
    val id: String,
    @SerializedName("user")
    var user: UserResponse,
    @SerializedName("title")
    var title: String,
    @SerializedName("updated_at")
    var updatedDate: String,
    var body: String,
    @SerializedName("html_url")
    var htmlUrl: String
)