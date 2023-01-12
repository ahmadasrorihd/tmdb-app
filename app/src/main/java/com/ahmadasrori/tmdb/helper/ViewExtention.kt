package com.ahmadasrori.tmdb.helper

import android.widget.ImageView

fun ImageView.setImageUrl(url: String) {
    GlideApp.with(context)
        .load(url)
        .centerInside()
        .into(this)
}