package com.honestyandpassion.ourcountry.MainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.honestyandpassion.ourcountry.Adapter.SubCategoryAdapter
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_sub_category.*

class SubCategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)
        var intent = intent
        var categoryType:String = intent.getStringExtra("categoryType")

        val grainList = arrayListOf("과일", "견과류", "쌀/잡곡", "채소", "건과일")
        val aquaticList = arrayListOf("건어물", "생선", "젓갈", "해산물/어패류", "김/해초")
        val healthList = arrayListOf("한방재료", "과일즙", "홍삼/인삼", "건강즙", "건강분말", "꿀")
        val meatList = arrayListOf("닭/오리고기", "쇠고기", "알류", "기타육류", "돼지고기")
        val fermentedList = arrayListOf("고추장/된장", "메주/간장", "청국장/쌈장")



        var toolbar: androidx.appcompat.widget.Toolbar = findViewById(com.honestyandpassion.ourcountry.R.id.toolbar_subcategory)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(categoryType)

        if(categoryType=="농산물")
        {
            rvSetting(grainList, categoryType)
        }
        else if(categoryType=="수산물")
        {
            rvSetting(aquaticList, categoryType)
        }
        else if(categoryType=="건강식품")
        {
            rvSetting(healthList, categoryType)
        }
        else if(categoryType=="축산물")
        {
            rvSetting(meatList, categoryType)
        }
        else if(categoryType=="발효식품")
        {
            rvSetting(fermentedList, categoryType)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId)
        {
            android.R.id.home-> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun rvSetting(categoryList:ArrayList<String>, categoryType:String) {
        rv_subcategory.setHasFixedSize(true)
        rv_subcategory.layoutManager = GridLayoutManager(this, 2)
        rv_subcategory.adapter = SubCategoryAdapter(this, categoryList, categoryType)
    }
}
