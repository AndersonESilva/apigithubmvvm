package com.anderson.apigithub_mvvm.feature.pullRequest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.anderson.apigithub_mvvm.common.Coroutines
import com.anderson.apigithub_mvvm.data.presentation.PullRequestPresentation
import com.anderson.apigithub_mvvm.data.presentation.RepositoryPresentation
import com.anderson.apigithub_mvvm.data.response.Resource
import com.anderson.apigithub_mvvm.feature.common.BaseViewModel
import com.anderson.apigithub_mvvm.feature.pullRequest.converter.PullRequestConverter
import com.anderson.apigithub_mvvm.service.GitHubRepository
import com.anderson.apigithub_mvvm.service.PullRequestRepository
import kotlinx.coroutines.Job
import javax.inject.Inject

/**
 * Created by anderson on 22/09/19.
 */
class PullRequestViewModel @Inject constructor(private val repository: GitHubRepository,
                                               private val converter: PullRequestConverter,
                                               private val repositoryx: PullRequestRepository) : BaseViewModel(){


    private val _resource = MutableLiveData<Resource<List<PullRequestPresentation>>>()
    private lateinit var job: Job

    val resource: LiveData<Resource<List<PullRequestPresentation>>>
        get() = _resource

    init {
        _resource.value = Resource.init()
    }

    fun getPullRequestes(creator : String, repositoryName: String){
        job = Coroutines.ioThenMain(
            { repository.getPullRequestes(creator, repositoryName) }, {
                if(it?.data != null){
                    val list = mutableListOf<PullRequestPresentation>()
                    _resource.value?.data?.let { it1 -> list.addAll(it1) }
                    val listConverted = converter.convert(it.data)
                    list.addAll(listConverted)
                    _resource.value = Resource.success(list)
                }else{
                    _resource.value = it?.error?.let { it1 -> Resource.error(it1) }
                }
            }
        )
    }

    val lisAux : MutableList<PullRequestPresentation> = arrayListOf()

    fun getListRepositoryLiveDate(creator : String, repositoryName: String): LiveData<List<PullRequestPresentation>> {

        return Transformations.map(repositoryx.getPullsRequest(creator, repositoryName)){

            if(it != null){
                it.forEach { input ->
                    lisAux.add(
                        PullRequestPresentation(
                            input.user.login,
                            input.user.avatarUrl,
                            input.title,
                            formtDate(input.updatedDate),
                            input.body,
                            input.htmlUrl
                        )
                    )
                }

                lisAux
            }else{
                null
            }

            lisAux
        }
    }

    private fun formtDate(dateString: String): String{

        //LocalDate.parse(input.updatedDate, DateTimeFormatter.ISO_DATE).toString()

        return dateString
    }
}
