package com.anderson.apigithub_mvvm.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.anderson.apigithub_mvvm.R

/**
 * Created by anderson on 16/11/2020.
 */
class ProgressDialog(context: Context) : Dialog(context) {
    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.setContentView(R.layout.dialog_progress)
        setCancelable(false)
    }
}