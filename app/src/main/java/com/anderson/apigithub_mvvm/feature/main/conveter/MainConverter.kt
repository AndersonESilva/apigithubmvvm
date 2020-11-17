package com.anderson.apigithub_mvvm.feature.main.conveter

import com.anderson.apigithub_mvvm.data.presentation.RepositoryPresentation
import com.anderson.apigithub_mvvm.data.response.RepositoryResponse
import com.anderson.apigithub_mvvm.feature.common.BaseConverter
import javax.inject.Inject

/**
 * Created by anderson on 16/11/2020.
 */
class MainConverter @Inject constructor() : BaseConverter<List<RepositoryResponse>, List<RepositoryPresentation>>{

    override fun convert(input: List<RepositoryResponse>): List<RepositoryPresentation> {
        val data = mutableListOf<RepositoryPresentation>()
        input.forEach { input ->
            data.add(
                RepositoryPresentation(
                    input.id,
                    input.name,
                    input.ownerResponse.login,
                    input.starsCount.toString(),
                    input.forksCount.toString(),
                    input.pullsUrls,
                    input.ownerResponse.avatarUrl
                ))
        }
        return data
    }

}