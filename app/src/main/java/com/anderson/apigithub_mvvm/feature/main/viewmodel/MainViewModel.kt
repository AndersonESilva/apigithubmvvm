package com.anderson.apigithub_mvvm.feature.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.anderson.apigithub_mvvm.common.Coroutines
import com.anderson.apigithub_mvvm.feature.common.BaseViewModel
import com.anderson.apigithub_mvvm.data.presentation.RepositoryPresentation
import com.anderson.apigithub_mvvm.data.response.RepositoryResponse
import com.anderson.apigithub_mvvm.data.response.Resource
import com.anderson.apigithub_mvvm.feature.main.conveter.MainConverter
import com.anderson.apigithub_mvvm.service.GitHubRepository
import com.anderson.apigithub_mvvm.service.MainRepository
import kotlinx.coroutines.Job
import javax.inject.Inject

/**
 * Created by anderson on 21/09/19.
 */
class MainViewModel @Inject constructor(private val repository: GitHubRepository,
                                        private val converter: MainConverter,
                                        private val repositoryX: MainRepository) : BaseViewModel() {

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
            _resource.value = Resource.loading()
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

    val lisAux : MutableList<RepositoryPresentation> = arrayListOf()

    fun getListRepositoryLiveDate(page: Int): LiveData<List<RepositoryPresentation>> {

        return Transformations.map(repositoryX.getListRepository(page)){

            var listRepositoryResponse: List<RepositoryResponse> = it.items

            if(listRepositoryResponse != null){
                listRepositoryResponse.forEach { input ->
                    lisAux.add(
                        RepositoryPresentation(
                            input.name,
                            input.ownerResponse.login,
                            input.starsCount.toString(),
                            input.forksCount.toString(),
                            input.pullsUrls,
                            input.ownerResponse.avatarUrl
                        ))
                }

                lisAux
            }else{
                null
            }
        }
    }
}