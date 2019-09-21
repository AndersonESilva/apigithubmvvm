package com.anderson.apigithub_mvvm.service

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.anderson.apigithub_mvvm.data.response.RepositoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(val service: GitHutService) {

    fun getListRepository(page : Int): MutableLiveData<List<RepositoryResponse>> {
        val data = MutableLiveData<List<RepositoryResponse>>()

        service.getListRepository("language:Java","stars",page).enqueue(object : Callback<List<RepositoryResponse>> {

            override fun onResponse(call: Call<List<RepositoryResponse>>, response: Response<List<RepositoryResponse>>) {
                Log.e("Sucesso", response.body().toString())
                data.value = response.body()
            }

            override fun onFailure(call: Call<List<RepositoryResponse>>, t: Throwable) {
                Log.e("CALLBACK", t.message)
            }

        })

        return data
    }
}