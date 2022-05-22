package com.anderson.apigithub_mvvm.feature.common

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.anderson.apigithub_mvvm.R
import com.anderson.apigithub_mvvm.ui.ProgressDialog

/**
 * Created by anderson on 21/09/19.
 */
abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    lateinit var bind: T

    private val progressDialog by lazy { ProgressDialog(this) }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun init()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = DataBindingUtil.setContentView(this, getLayoutId())
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
