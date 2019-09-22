package com.anderson.apigithub_mvvm.feature.main.activity

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
    private var page: Int = 1

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun init() {
        bind.viewModel = viewModel

        viewModel.getListRepository(page)

        viewModel.mMediatorRespositories.observe(this, Observer {
            if(it != null){
                repositoryAdapter = RepositoryAdapter(it as ArrayList<RepositoryPresentation>)
            }
        })

//        bind.listView.setOnScrollListener(object : AbsListView.OnScrollListener{
//
//            override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
//                var lastInScreen = firstVisibleItem + visibleItemCount
//
////                if(lastInScreen == totalItemCount){
////                    page++
////                }
//            }
//
//            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//        })

    }
}
