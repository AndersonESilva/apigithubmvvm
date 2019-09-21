package com.anderson.apigithub_mvvm.feature.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import br.com.anderson.apigithub_mvvm.ui.generic.base.viewmodel.BaseViewModel
import com.anderson.apigithub_mvvm.data.presentation.RepositoryPresentation
import com.anderson.apigithub_mvvm.service.MainRepository
import javax.inject.Inject

/**
 * Created by anderson on 21/09/19.
 */
class MainViewModel @Inject constructor(val repository: MainRepository) : BaseViewModel() {

    val lisAux : MutableList<RepositoryPresentation> = arrayListOf()

    var mMediatorRespositories = MediatorLiveData<List<RepositoryPresentation>>()
    var mLiveDataRespositories = MutableLiveData<List<RepositoryPresentation>>()

    fun getListRepository(page: Int){

        mMediatorRespositories.addSource(getListRepositoryLiveDate(page)){
            if(it != null){
                mLiveDataRespositories.value = it
            }
        }
    }

    fun getListRepositoryLiveDate(page: Int): LiveData<List<RepositoryPresentation>> {

        val dataLiveData: LiveData<List<RepositoryPresentation>> = Transformations.map(repository.getListRepository(page)){

            if(it != null){
                it.forEach { input ->
                    lisAux.add(
                        RepositoryPresentation(
                            input.name,
                            input.imgUrl,
                            input.starsCount.toString(),
                            input.forksCount.toString(),
                            input.pullsUrls
                        ))
                }

                lisAux
            }else{
                null
            }
        }

        return dataLiveData
    }
}