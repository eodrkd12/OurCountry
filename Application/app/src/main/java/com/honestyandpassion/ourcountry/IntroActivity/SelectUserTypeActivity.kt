package com.honestyandpassion.ourcountry.IntroActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.honestyandpassion.ourcountry.Adapter.SelectCategoryAdapter
import com.honestyandpassion.ourcountry.Adapter.SelectUserTypeAdapter
import com.honestyandpassion.ourcountry.Class.ToolbarSetting
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_select_user_type.*

class SelectUserTypeActivity : ToolbarSetting() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_user_type)

        var toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_selectusertype)
        toolbarBinding(toolbar, "회원등급을 설정해주세요.")

        var userTypeList = arrayListOf("구매자", "판매자")
        rv_selectusertype.setHasFixedSize(true)
        rv_selectusertype.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_selectusertype.adapter = SelectUserTypeAdapter(this , userTypeList)
    }
}
