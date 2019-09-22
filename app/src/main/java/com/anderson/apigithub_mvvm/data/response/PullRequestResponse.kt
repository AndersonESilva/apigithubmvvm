package com.anderson.apigithub_mvvm.data.response

import com.google.gson.annotations.SerializedName

data class PullRequestResponse(
    var user: UserResponse,
    var title: String,
    @SerializedName("updated_at")
    var updatedDate: String,
    var body: String
)