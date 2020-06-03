package com.honestyandpassion.ourcountry.MainActivity

import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.honestyandpassion.ourcountry.Adapter.ProductAllViewAdapter
import com.honestyandpassion.ourcountry.Class.ToolbarSetting
import com.honestyandpassion.ourcountry.Item.PreviewItem
import com.honestyandpassion.ourcountry.Item.Product
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_search.*
import org.json.JSONObject

class SearchActivity : ToolbarSetting() {

    var searchList = ArrayList<PreviewItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        var toolbar : Toolbar = findViewById(R.id.toolbar_search)
        var intent = intent
        var searchText: String = intent.getStringExtra("searchText")

        toolbarBinding(toolbar, searchText)

        VolleyService.searchRegisterReq(searchText, this ,{ success ->
            searchList.clear()
            var array = success
            for (i in 0..array!!.length() - 1) {
                var json = array[i] as JSONObject
                var product = PreviewItem(
                    json.getInt("register_id"),
                    json.getString("register_title"),
                    json.getString("product_price"),
                    json.getString("product_status"),
                    ArrayList<Bitmap>()
                )
                searchList.add(product)
            }
            rv_searchlist.setHasFixedSize(true)
            rv_searchlist.layoutManager=
                GridLayoutManager(this, 2)
            rv_searchlist.adapter= ProductAllViewAdapter(this, searchList)
        })
    }
}
