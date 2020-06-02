package com.honestyandpassion.ourcountry.Item

import android.graphics.Bitmap

data class Preview(
    val registerId: Int?,
    val registerTitle: String?,
    val productPrice: String?,
    val productStatus: String?,
    var imageList: ArrayList<Bitmap>?
)