package com.honestyandpassion.ourcountry.MainActivity

import android.os.Bundle
import com.honestyandpassion.ourcountry.Class.ToolbarSetting
import com.honestyandpassion.ourcountry.R

class ReviewActivity:ToolbarSetting() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        var toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_review)
        toolbarBinding(toolbar, "리뷰등록")
        }
    }