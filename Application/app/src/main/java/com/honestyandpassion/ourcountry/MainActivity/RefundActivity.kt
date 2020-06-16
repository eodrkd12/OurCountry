package com.honestyandpassion.ourcountry.MainActivity

import android.os.Bundle
import com.honestyandpassion.ourcountry.Class.ToolbarSetting
import com.honestyandpassion.ourcountry.R

class RefundActivity: ToolbarSetting() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refund)
    }
}