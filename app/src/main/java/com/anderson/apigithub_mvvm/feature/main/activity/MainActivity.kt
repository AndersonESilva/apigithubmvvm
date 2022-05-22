package com.anderson.apigithub_mvvm.feature.main.activity

import android.view.View
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
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by anderson on 21/09/19.
 */
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_main

    private val viewModel: MainViewModel by viewModel()
    private lateinit var adapter: RepositoryAdapter
    private var isLoading = false

    override fun init() {

        initToolbar()
        initRecyclerView()
        observeResource()
        viewModel.getRepositories()
    }

    private fun initToolbar(){
        supportActionBar?.title = resources.getString(R.string.application_name)
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
        viewModel.resource.observe(this) { resource ->
            when (resource.status) {
                Resource.Status.INIT -> {
                    showLoading()
                }
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
        }
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
