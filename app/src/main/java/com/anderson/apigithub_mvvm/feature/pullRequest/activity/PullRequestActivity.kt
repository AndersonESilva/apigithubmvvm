package com.anderson.apigithub_mvvm.feature.pullRequest.activity

import br.com.anderson.apigithub_mvvm.ui.generic.base.activity.BaseActivity
import com.anderson.apigithub_mvvm.R
import com.anderson.apigithub_mvvm.databinding.ActivityPullRequestBinding
import com.anderson.apigithub_mvvm.feature.pullRequest.viewmodel.PullRequestViewModel

/**
 * Created by anderson on 22/09/19.
 */
class PullRequestActivity : BaseActivity<ActivityPullRequestBinding, PullRequestViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_pull_request
    }

    override fun getViewModelClass(): Class<PullRequestViewModel> = PullRequestViewModel::class.java

    override fun init() {

        bind.viewModel = viewModel


    }

}
