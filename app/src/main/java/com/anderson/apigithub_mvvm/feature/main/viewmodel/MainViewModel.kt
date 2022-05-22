package com.anderson.apigithub_mvvm.feature.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.anderson.apigithub_mvvm.data.presentation.RepositoryPresentation
import com.anderson.apigithub_mvvm.data.response.Resource
import com.anderson.apigithub_mvvm.feature.common.BaseViewModel
import com.anderson.apigithub_mvvm.feature.main.conveter.MainConverter
import com.anderson.apigithub_mvvm.service.GitHubRepository
import kotlinx.coroutines.launch

/**
 * Created by anderson on 21/09/19.
 */
class MainViewModel(private val repository: GitHubRepository,
                    private val converter: MainConverter): BaseViewModel() {

    private val _resource = MutableLiveData<Resource<List<RepositoryPresentation>>>()
    private var page = 0

    val resource: LiveData<Resource<List<RepositoryPresentation>>>
        get() = _resource

    init {
        _resource.value = Resource.init()
    }

    fun getRepositories(){
        if(page > 0){
            _resource.value = Resource.loading(_resource.value?.data)
        }
        page++

        viewModelScope.launch {
            val response = repository.getRepositories(page)
            if(response.data != null){
                val list = mutableListOf<RepositoryPresentation>()
                _resource.value?.data?.let { it1 -> list.addAll(it1) }
                val listConverted = converter.convert(response.data.items)
                list.addAll(listConverted)
                _resource.value = Resource.success(list)
            }else{
                _resource.value = response.error?.let { it1 -> Resource.error(it1) }
            }
        }
    }
}
