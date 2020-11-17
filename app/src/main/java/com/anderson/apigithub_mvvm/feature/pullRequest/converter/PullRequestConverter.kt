package com.anderson.apigithub_mvvm.feature.pullRequest.converter

import com.anderson.apigithub_mvvm.data.presentation.PullRequestPresentation
import com.anderson.apigithub_mvvm.data.response.PullRequestResponse
import com.anderson.apigithub_mvvm.feature.common.BaseConverter
import javax.inject.Inject

/**
 * Created by anderson on 16/11/2020.
 */
class PullRequestConverter @Inject constructor() : BaseConverter<List<PullRequestResponse>, List<PullRequestPresentation>> {

    override fun convert(input: List<PullRequestResponse>): List<PullRequestPresentation> {
        val data = mutableListOf<PullRequestPresentation>()
        input.forEach { input ->
            data.add(
                PullRequestPresentation(
                    input.id,
                    input.user.login,
                    input.user.avatarUrl,
                    input.title,
                    input.updatedDate,
                    input.body,
                    input.htmlUrl
                )
            )
        }
        return data
    }

}
