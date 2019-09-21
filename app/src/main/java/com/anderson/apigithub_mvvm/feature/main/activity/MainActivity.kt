package com.anderson.apigithub_mvvm.feature.main.activity

import br.com.anderson.apigithub_mvvm.ui.generic.base.activity.BaseActivity
import com.anderson.apigithub_mvvm.R
import com.anderson.apigithub_mvvm.databinding.ActivityMainBinding
import com.anderson.apigithub_mvvm.feature.main.viewmodel.MainViewModel

/**
 * Created by anderson on 21/09/19.
 */
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun init() {
        bind.viewModel = viewModel
    }
}
