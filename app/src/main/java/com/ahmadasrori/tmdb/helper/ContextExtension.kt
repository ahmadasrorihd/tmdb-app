package com.ahmadasrori.tmdb.helper

import android.content.Context
import android.widget.Toast

fun Context.showToast(message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

