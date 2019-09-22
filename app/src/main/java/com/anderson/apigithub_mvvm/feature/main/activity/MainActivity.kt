package com.anderson.apigithub_mvvm.feature.main.activity

import android.app.Activity
import android.widget.AbsListView
import androidx.lifecycle.Observer
import br.com.anderson.apigithub_mvvm.ui.generic.base.activity.BaseActivity
import com.anderson.apigithub_mvvm.R
import com.anderson.apigithub_mvvm.data.presentation.RepositoryPresentation
import com.anderson.apigithub_mvvm.databinding.ActivityMainBinding
import com.anderson.apigithub_mvvm.feature.main.adapter.RepositoryAdapter
import com.anderson.apigithub_mvvm.feature.main.viewmodel.MainViewModel

/**
 * Created by anderson on 21/09/19.
 */
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private lateinit var repositoryAdapter: RepositoryAdapter
    private var currentPage: Int = 1
    private var previousTotalItemCount: Int = 1
    private var visibleThreshold : Int = 1
    private var loading = true
    private var end = false

    private lateinit var activity: Activity

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun init() {
        bind.viewModel = viewModel
        activity = this

        initPage1()
        initScroll()
    }

    private fun initPage1(){
        viewModel.getListRepositoryLiveDate(currentPage).observe(this, Observer {
            repositoryAdapter = RepositoryAdapter(it as ArrayList<RepositoryPresentation>)

            bind.listView.adapter = repositoryAdapter
        })
    }

    private fun initScroll(){

        bind.listView.setOnScrollListener(object : AbsListView.OnScrollListener{

            override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                if(!end){
                    if (totalItemCount < previousTotalItemCount) {
                        currentPage = 1
                        previousTotalItemCount = totalItemCount
                        if (totalItemCount == 0) { loading = true; }
                    }

                    if (loading && (totalItemCount > previousTotalItemCount)) {
                        loading = false
                        previousTotalItemCount = totalItemCount
                        currentPage++
                    }

                    if (!loading && (firstVisibleItem + visibleItemCount + visibleThreshold) >= totalItemCount ) {

                        loading = true
                        viewModel.getListRepositoryLiveDate(currentPage).observe(activity as MainActivity, Observer {
                            if(it != null){
                                repositoryAdapter = RepositoryAdapter(it as ArrayList<RepositoryPresentation>)

                                bind.listView.adapter = repositoryAdapter

                                bind.listView.setSelection(firstVisibleItem + 2)
                            }else{
                                end = true
                            }
                        })
                    }

                }

            }

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
            }

        })
    }
}
