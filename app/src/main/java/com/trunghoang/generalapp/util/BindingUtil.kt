package com.trunghoang.generalapp.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * Created by Hoang Trung on 18/07/2019
 */

@BindingAdapter("glideUrl")
fun ImageView.setGlideUrl(url: String?) {
    Glide.with(context)
        .load(url)
        .into(this)
}