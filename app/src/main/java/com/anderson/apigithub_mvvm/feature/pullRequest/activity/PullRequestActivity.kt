package com.anderson.apigithub_mvvm.feature.pullRequest.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
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

        fun start(source: Activity){
            val intent = Intent(source, PullRequestActivity::class.java)
            source.startActivity(intent)
        }
    }

    private lateinit var repositoryPresentation: RepositoryPresentation
    private lateinit var pullRequestAdapter: PullRequestAdapter

    override fun getLayoutId(): Int {
        return R.layout.activity_pull_request
    }

    override fun getViewModelClass(): Class<PullRequestViewModel> = PullRequestViewModel::class.java

    override fun init() {
        bind.viewModel = viewModel

        repositoryPresentation = (intent.extras?.getSerializable(REPO_OBJ) as? RepositoryPresentation)!!

        initToolbar()
        initListView()
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
                Resource.Status.SUCCESS -> {}
                Resource.Status.LOADING -> {}
                Resource.Status.ERROR -> {}
            }
        })
    }

    private fun initListView(){
        viewModel.getListRepositoryLiveDate(repositoryPresentation.login, repositoryPresentation.name).observe(this, Observer {
            pullRequestAdapter = PullRequestAdapter(it as ArrayList<PullRequestPresentation>)

            bind.listView.adapter = pullRequestAdapter
            bind.progressBar.visibility = View.INVISIBLE
            onClickItem(it)
        })
    }

    private fun onClickItem(it: ArrayList<PullRequestPresentation> ){

        bind.listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedItem = it.get(position)

                val openUrl = Intent(Intent.ACTION_VIEW)
                openUrl.data = Uri.parse(selectedItem.htmlUrl)
                startActivity(openUrl)
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
