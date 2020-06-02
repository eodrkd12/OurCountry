package com.honestyandpassion.ourcountry.Item

import android.graphics.Bitmap

data class PreviewItem(
    val registerId: Int?,
    val registerTitle: String?,
    val productPrice: String?,
    val productStatus: String?,
    var imageList: ArrayList<Bitmap>?
)