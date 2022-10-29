package com.sample.myshop.presentation.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.sample.myshop.R

@BindingAdapter("urlToImage")
fun ImageView.setUrlToImage(url: String?) {
    url.let {
        Glide.with(this)
                .load(it)
                .centerCrop()
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .fallback(R.drawable.ic_image)
                .into(this)
    }
}