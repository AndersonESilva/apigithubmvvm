package com.anderson.apigithub_mvvm.service

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.anderson.apigithub_mvvm.data.response.PullRequestResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PullRequestRepository @Inject constructor(val service: GitHutService){

    fun getPullsRequest(creator : String, repository: String): MutableLiveData<List<PullRequestResponse>> {
        val data = MutableLiveData<List<PullRequestResponse>>()

        service.getPullsRequest(creator,repository).enqueue(object : Callback<List<PullRequestResponse>> {

            override fun onResponse(call: Call<List<PullRequestResponse>>, response: Response<List<PullRequestResponse>>) {
                Log.e("Sucesso", response.body().toString())
                if(response.body() != null){
                    data.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<PullRequestResponse>>, t: Throwable) {
                Log.e("CALLBACK", t.message)
            }

        })

        return data
    }
}