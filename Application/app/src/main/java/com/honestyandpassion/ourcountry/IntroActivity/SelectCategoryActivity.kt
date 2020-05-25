package com.honestyandpassion.ourcountry.IntroActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.honestyandpassion.ourcountry.Adapter.SelectCategoryAdapter
import com.honestyandpassion.ourcountry.Class.ToolbarSetting
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_select_category.*

class SelectCategoryActivity : ToolbarSetting() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_category)

        var toolbar: androidx.appcompat.widget.Toolbar = findViewById(com.honestyandpassion.ourcountry.R.id.toolbar_selectcategory)
        toolbarBinding(toolbar, "카테고리를 선택 해주세요.")

        val categoryList = arrayListOf("농산물", "수산물", "축산물", "건강식품", "발효식품")

        rv_selectcategory.setHasFixedSize(true)
        rv_selectcategory.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_selectcategory.adapter = SelectCategoryAdapter(this , categoryList)
    }

}
