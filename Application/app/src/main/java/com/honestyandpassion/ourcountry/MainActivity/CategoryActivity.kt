package com.honestyandpassion.ourcountry.MainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.honestyandpassion.ourcountry.Adapter.FragmentCategoryAdapter
import com.honestyandpassion.ourcountry.Class.ToolbarSetting
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_category.*

class CategoryActivity : ToolbarSetting() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        var toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_category)
        toolbarBinding(toolbar, "카테고리")

        val categoryList = arrayListOf("농산물", "수산물", "축산물", "건강식품", "발효식품")

        rv_activity_category.setHasFixedSize(true)
        rv_activity_category.layoutManager = GridLayoutManager(this, 2)
        rv_activity_category.adapter = FragmentCategoryAdapter(this, categoryList)


    }
}
