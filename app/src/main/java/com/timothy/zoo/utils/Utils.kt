package com.timothy.zoo.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.ImageView
import android.widget.TimePicker
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.timothy.zoo.R
import timber.log.Timber


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
        Timber.d("url:$url")
        Glide.with(image.context).load(url).centerCrop()
            .placeholder(R.drawable.no_image_placeholder)
            .into(image)
    }
}