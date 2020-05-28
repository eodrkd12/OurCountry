package com.honestyandpassion.ourcountry.MainActivity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.honestyandpassion.ourcountry.Adapter.SearchProductAdapter
import com.honestyandpassion.ourcountry.Class.ToolbarSetting
import com.honestyandpassion.ourcountry.Fragment.HomeFragment
import com.honestyandpassion.ourcountry.Item.Product
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_product_all_view.*

class ProductAllViewActivity : ToolbarSetting() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_all_view)

        var intent = intent
        var clickedText:String = intent.getStringExtra("clickedText")

        var toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_recent_register)


        if(clickedText == "최근등록된상품") {
            toolbarBinding(toolbar, "최근 등록된 상품")
            activityBinding(HomeFragment.recentProductArrayList)
        }
        else if(clickedText == "인기상품") {
            toolbarBinding(toolbar, "인기상품")
            activityBinding(HomeFragment.popularProductArrayList)
        }



    }

    fun activityBinding(productList: ArrayList<Product>) {
        if(productList.size == 0) {
            image_emptyrecent.visibility = View.VISIBLE
            text_emptyrecent1.visibility = View.VISIBLE
            text_emptyrecent2.visibility = View.VISIBLE
        }
        else {
            rv_recent_register.setHasFixedSize(true)
            rv_recent_register.layoutManager = GridLayoutManager(this, 2)
            rv_recent_register.adapter = SearchProductAdapter(this, productList)
        }
    }
}
