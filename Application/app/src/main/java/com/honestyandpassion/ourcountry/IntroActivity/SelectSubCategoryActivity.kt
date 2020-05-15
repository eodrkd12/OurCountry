package com.honestyandpassion.ourcountry.IntroActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.Adapter.SelectCategoryAdapter
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_select_sub_category.*

class SelectSubCategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_sub_category)

        val grainList = arrayListOf("과일", "견과류", "쌀/잡곡", "채소", "건과일")
        val aquaticList = arrayListOf("건어물", "생선", "젓갈", "해산물/어패류", "김/해초")
        val healthList = arrayListOf("한방재료", "과일즙", "홍삼/인삼", "건강즙", "건강분말", "꿀")
        val meatList = arrayListOf("닭/오리고기", "쇠고기", "알류", "기타육류", "돼지고기")
        val fermentedList = arrayListOf("고추장/된장", "메주/간장", "청국장/쌈장")

        var intent = intent
        var categoryType:String = intent.getStringExtra("categoryType")

        if(categoryType == "농산물")
        {
            subCategoryRv(grainList)
        }
        else if(categoryType=="수산물")
        {
            subCategoryRv(aquaticList)
        }
        else if(categoryType=="축산물")
        {
            subCategoryRv(meatList)
        }
        else if(categoryType=="건강식품")
        {
            subCategoryRv(healthList)
        }
        else if(categoryType=="발효식품")
        {
            subCategoryRv(fermentedList)
        }


        var toolbar: androidx.appcompat.widget.Toolbar = findViewById(com.honestyandpassion.ourcountry.R.id.toolbar_selectsubcategory)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("하위 카테고리를 선택해주세요.")
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId)
        {
            android.R.id.home-> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun subCategoryRv(categoryList:ArrayList<String>) {
        rv_selectsubcategory.setHasFixedSize(true)
        rv_selectsubcategory.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_selectsubcategory.adapter = SelectCategoryAdapter(this , categoryList)
    }
}
