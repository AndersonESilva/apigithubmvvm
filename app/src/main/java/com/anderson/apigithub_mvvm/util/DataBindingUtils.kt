package com.anderson.apigithub_mvvm.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

object DataBindingUtils {

    @JvmStatic
    @BindingAdapter("img_url")
    fun setPicassoImage(imageView: ImageView, imgUrl: String?) {
        if (imgUrl == null) return
        Picasso.get()
            .load(imgUrl)
            .into(imageView)
    }
}