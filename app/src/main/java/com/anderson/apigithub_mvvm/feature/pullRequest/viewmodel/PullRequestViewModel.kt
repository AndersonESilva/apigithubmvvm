package com.anderson.apigithub_mvvm.feature.pullRequest.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import br.com.anderson.apigithub_mvvm.ui.generic.base.viewmodel.BaseViewModel
import com.anderson.apigithub_mvvm.data.presentation.PullRequestPresentation
import com.anderson.apigithub_mvvm.service.PullRequestRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

/**
 * Created by anderson on 22/09/19.
 */
class PullRequestViewModel @Inject constructor(val repository: PullRequestRepository) : BaseViewModel(){

    val lisAux : MutableList<PullRequestPresentation> = arrayListOf()

    @RequiresApi(Build.VERSION_CODES.O)
    fun getListRepositoryLiveDate(creator : String, repositoryName: String): LiveData<List<PullRequestPresentation>> {

        return Transformations.map(repository.getPullsRequest(creator, repositoryName)){

            if(it != null){
                it.forEach { input ->
                    lisAux.add(
                        PullRequestPresentation(
                            input.user.login,
                            input.user.avatarUrl,
                            input.title,
                            LocalDate.parse(input.updatedDate, DateTimeFormatter.ISO_DATE),
                            input.body
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
}