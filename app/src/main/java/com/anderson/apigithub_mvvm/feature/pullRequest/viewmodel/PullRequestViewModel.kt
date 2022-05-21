package com.anderson.apigithub_mvvm.feature.pullRequest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.anderson.apigithub_mvvm.data.presentation.PullRequestPresentation
import com.anderson.apigithub_mvvm.data.response.Resource
import com.anderson.apigithub_mvvm.feature.common.BaseViewModel
import com.anderson.apigithub_mvvm.feature.pullRequest.converter.PullRequestConverter
import com.anderson.apigithub_mvvm.service.GitHubRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by anderson on 22/09/19.
 */
class PullRequestViewModel @Inject constructor(private val repository: GitHubRepository,
                                               private val converter: PullRequestConverter) : BaseViewModel(){


    private val _resource = MutableLiveData<Resource<List<PullRequestPresentation>>>()
    private lateinit var job: Job

    val resource: LiveData<Resource<List<PullRequestPresentation>>>
        get() = _resource

    init {
        _resource.value = Resource.init()
    }

    fun getPullRequestes(creator : String, repositoryName: String){
        viewModelScope.launch {
            val response = repository.getPullRequestes(creator, repositoryName)
            if(response.data != null){
                val list = mutableListOf<PullRequestPresentation>()
                _resource.value?.data?.let { it1 -> list.addAll(it1) }
                val listConverted = converter.convert(response.data)
                list.addAll(listConverted)
                _resource.value = Resource.success(list)
            }else{
                _resource.value = response.error?.let { it1 -> Resource.error(it1) }
            }
        }
    }
}
