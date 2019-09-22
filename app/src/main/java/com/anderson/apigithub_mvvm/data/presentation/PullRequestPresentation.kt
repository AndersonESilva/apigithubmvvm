package com.anderson.apigithub_mvvm.data.presentation

import java.util.*

data class PullRequestPresentation(
    var loginUser: String,
    var avatarUrlUser: String,
    var title: String,
    var data: Date,
    var body: String
)