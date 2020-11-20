package com.anderson.apigithub_mvvm.feature.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anderson.apigithub_mvvm.common.Coroutines
import com.anderson.apigithub_mvvm.feature.common.BaseViewModel
import com.anderson.apigithub_mvvm.data.presentation.RepositoryPresentation
import com.anderson.apigithub_mvvm.data.response.Resource
import com.anderson.apigithub_mvvm.feature.main.conveter.MainConverter
import com.anderson.apigithub_mvvm.service.GitHubRepository
import kotlinx.coroutines.Job
import javax.inject.Inject

/**
 * Created by anderson on 21/09/19.
 */
class MainViewModel @Inject constructor(private val repository: GitHubRepository,
                                        private val converter: MainConverter) : BaseViewModel() {

    private val _resource = MutableLiveData<Resource<List<RepositoryPresentation>>>()
    private lateinit var job: Job
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
        job = Coroutines.ioThenMain(
            { repository.getRepositories(page) }, {
                if(it?.data != null){
                    val list = mutableListOf<RepositoryPresentation>()
                    _resource.value?.data?.let { it1 -> list.addAll(it1) }
                    val listConverted = converter.convert(it.data.items)
                    list.addAll(listConverted)
                    _resource.value = Resource.success(list)
                }else{
                    _resource.value = it?.error?.let { it1 -> Resource.error(it1) }
                }
            }
        )
    }
}
