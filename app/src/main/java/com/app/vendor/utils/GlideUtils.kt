package com.app.vendor.utils

import android.widget.ImageView
import com.app.vendor.R
import com.app.vendor.base.App
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


object GlideUtils {
    fun loadImageFullWidth(view: ImageView?, url: String?) {

        view?.let {
            Glide.with(App.INSTANCE)
                .load(url)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.ic_placeholder)
                        .error(R.drawable.ic_placeholder)
                        .skipMemoryCache(false)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(it)

        }

    }

}