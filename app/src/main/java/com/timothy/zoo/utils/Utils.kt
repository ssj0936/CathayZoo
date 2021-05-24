package com.timothy.zoo.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.timothy.zoo.R


fun isNetworkAvailable(context: Context):Boolean{
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

    return when{
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        else ->false
    }
}

@BindingAdapter("url")
fun setImage(image: AppCompatImageView, url: String?) {
    if (!url.isNullOrEmpty()){
        Glide.with(image.context).load(url).centerCrop()
            .placeholder(R.drawable.no_image_placeholder)
            .into(image)
    }
}

@BindingAdapter("html_text")
fun setHtmlText(view: TextView, text: String?) {
    if (text != null) {
        view.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT)
        view.movementMethod = LinkMovementMethod.getInstance()
    } else {
        view.text = ""
    }
}