package com.anderson.apigithub_mvvm.feature.common

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.anderson.apigithub_mvvm.R
import com.anderson.apigithub_mvvm.ui.ProgressDialog
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * Created by anderson on 21/09/19.
 */
abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : AppCompatActivity() {

    protected lateinit var viewModel: V
    protected lateinit var bind: T

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory

    private val progressDialog by lazy { ProgressDialog(this) }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun getViewModelClass(): Class<V>

    protected abstract fun init()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        bind = DataBindingUtil.setContentView(this, getLayoutId())

        viewModel = ViewModelProviders.of(this, this.viewModelProvider).get(getViewModelClass())

        init()
    }

    fun showLoading() { progressDialog.takeUnless { it.isShowing }?.show() }
    fun hideLoading() { progressDialog.takeIf { it.isShowing }?.dismiss() }

    fun showError(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.generic_error_title)
            .setMessage(R.string.error_generic)
            .setPositiveButton(R.string.ok_understand) { dialog, _ ->
                dialog.dismiss()
            }.create().show()
    }
}
