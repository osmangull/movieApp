package com.sanalkasif.movieapp.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso

object ImageLoader {

    const val imageAdress = "https://image.tmdb.org/t/p/"

    fun loadImage(url: String?, imageView:ImageView){
        Picasso.get().load(imageAdress + "w500" + url).into(imageView)
    }
}