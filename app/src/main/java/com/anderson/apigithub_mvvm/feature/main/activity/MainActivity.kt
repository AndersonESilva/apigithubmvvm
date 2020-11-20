package com.anderson.apigithub_mvvm.feature.main.activity

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anderson.apigithub_mvvm.R
import com.anderson.apigithub_mvvm.data.presentation.RepositoryPresentation
import com.anderson.apigithub_mvvm.data.response.Resource
import com.anderson.apigithub_mvvm.databinding.ActivityMainBinding
import com.anderson.apigithub_mvvm.feature.common.BaseActivity
import com.anderson.apigithub_mvvm.feature.main.adapter.RepositoryAdapter
import com.anderson.apigithub_mvvm.feature.main.viewmodel.MainViewModel
import com.anderson.apigithub_mvvm.feature.pullRequest.activity.PullRequestActivity

/**
 * Created by anderson on 21/09/19.
 */
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    private lateinit var adapter: RepositoryAdapter
    private var isLoading = false

    override fun init() {
        bind.viewModel = viewModel

        initToolbar()
        initRecyclerView()
        observeResource()
        viewModel.getRepositories()
    }

    private fun initToolbar(){
        supportActionBar?.title = resources.getString(R.string.app_name)
    }

    private fun initRecyclerView(){
        adapter = RepositoryAdapter { clickInItem(it) }

        bind.recyclerMain.also {
            it.setHasFixedSize(true)
            it.layoutManager = LinearLayoutManager(this)
            it.addOnScrollListener(onScrollListener())
            it.adapter = adapter
        }
    }

    private fun observeResource(){
        viewModel.resource.observe(this, Observer { resource ->
            when(resource.status) {
                Resource.Status.INIT -> { showLoading() }
                Resource.Status.SUCCESS -> {
                    adapter.submitList(resource.data)
                    adapter.notifyDataSetChanged()
                    bind.progressMain.visibility = View.GONE
                    hideLoading()
                    isLoading = false
                }
                Resource.Status.LOADING -> {
                    bind.progressMain.visibility = View.VISIBLE
                    isLoading = true
                }
                Resource.Status.ERROR -> {
                    hideLoading()
                    showError()
                    isLoading = false
                }
            }
        })
    }

    private fun onScrollListener(): RecyclerView.OnScrollListener{
        return object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(!bind.recyclerMain.canScrollVertically(1)){
                    if(!isLoading){
                        viewModel.getRepositories()
                    }
                }
            }
        }
    }

    private fun clickInItem(item: RepositoryPresentation){
        PullRequestActivity.start(this, item)
    }

}
