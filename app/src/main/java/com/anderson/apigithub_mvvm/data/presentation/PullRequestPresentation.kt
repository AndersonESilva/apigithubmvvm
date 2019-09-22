package com.anderson.apigithub_mvvm.data.presentation

import java.time.LocalDate

data class PullRequestPresentation(
    var loginUser: String,
    var avatarUrlUser: String,
    var title: String,
    var data: String,
    var body: String
)