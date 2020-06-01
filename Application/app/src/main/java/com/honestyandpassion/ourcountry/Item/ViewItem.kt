package com.honestyandpassion.ourcountry.Item

import android.graphics.Bitmap

data class View(
    val userId: String?,
    val registerId: Int?,
    val viewDate: String?,
    val registerTitle: String?,
    var imageList: ArrayList<Bitmap>?
)