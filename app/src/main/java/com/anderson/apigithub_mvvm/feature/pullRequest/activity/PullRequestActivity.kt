package com.anderson.apigithub_mvvm.feature.pullRequest.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.anderson.apigithub_mvvm.R
import com.anderson.apigithub_mvvm.data.presentation.PullRequestPresentation
import com.anderson.apigithub_mvvm.data.presentation.RepositoryPresentation
import com.anderson.apigithub_mvvm.data.response.Resource
import com.anderson.apigithub_mvvm.databinding.ActivityPullRequestBinding
import com.anderson.apigithub_mvvm.feature.common.BaseActivity
import com.anderson.apigithub_mvvm.feature.pullRequest.adapter.PullRequestAdapter
import com.anderson.apigithub_mvvm.feature.pullRequest.viewmodel.PullRequestViewModel

/**
 * Created by anderson on 22/09/19.
 */
class PullRequestActivity : BaseActivity<ActivityPullRequestBinding, PullRequestViewModel>() {

    companion object {
        const val REPO_OBJ = "repoObj"

        fun start(source: Context, presentation: RepositoryPresentation){
            val intent = Intent(source, PullRequestActivity::class.java)
            intent.putExtra(REPO_OBJ, presentation)
            source.startActivity(intent)
        }
    }

    private lateinit var repositoryPresentation: RepositoryPresentation
    private lateinit var adapter: PullRequestAdapter

    override fun getLayoutId(): Int {
        return R.layout.activity_pull_request
    }

    override fun getViewModelClass(): Class<PullRequestViewModel> = PullRequestViewModel::class.java

    override fun init() {
        bind.viewModel = viewModel

        repositoryPresentation = (intent.extras?.getSerializable(REPO_OBJ) as? RepositoryPresentation)!!

        initToolbar()
        initRecyclerView()
        observeResource()
        startData()
    }

    fun startData(){
        viewModel.getPullRequestes(repositoryPresentation.login, repositoryPresentation.name)
    }

    private fun initRecyclerView(){
        adapter = PullRequestAdapter { clickInItem(it) }

        bind.recyclerPull.also {
            it.setHasFixedSize(true)
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = adapter
        }
    }

    private fun initToolbar(){
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.pull_requests)
    }

    private fun observeResource(){
        viewModel.resource.observe(this, Observer { resource ->
            when(resource.status) {
                Resource.Status.INIT -> { showLoading() }
                Resource.Status.SUCCESS -> {
                    hideLoading()
                    bind.progressPull.visibility = View.GONE
                    adapter.submitList(resource.data)
                    adapter.notifyDataSetChanged()

                }
                Resource.Status.LOADING -> {
                    bind.progressPull.visibility = View.VISIBLE
                }
                Resource.Status.ERROR -> {
                    hideLoading()
                    showError()
                }
            }
        })
    }

    private fun clickInItem(item: PullRequestPresentation){
        val openUrl = Intent(Intent.ACTION_VIEW)
        openUrl.data = Uri.parse(item.htmlUrl)
        startActivity(openUrl)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
