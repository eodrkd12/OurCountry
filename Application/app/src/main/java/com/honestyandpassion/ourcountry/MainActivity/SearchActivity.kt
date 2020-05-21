package com.honestyandpassion.ourcountry.MainActivity

import android.app.Dialog
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.honestyandpassion.ourcountry.Adapter.ProductAdapter
import com.honestyandpassion.ourcountry.Class.ToolbarSetting
import com.honestyandpassion.ourcountry.Item.Product
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_search.*
import org.json.JSONArray
import org.json.JSONObject

class SearchActivity : ToolbarSetting() {

    var searchList = ArrayList<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        var toolbar : Toolbar = findViewById(R.id.toolbar_search)
        var intent = intent
        var searchText: String = intent.getStringExtra("searchText")

        var width = rv_searchlist.height / 2

        toolbarBinding(toolbar, searchText)

        VolleyService.searchRegisterReq(searchText, this ,{ success ->
            searchList.clear()
            var array=success
            for(i in 0..array!!.length()-1){
                var json=array[i] as JSONObject
                var product=Product(json.getInt("register_id"),
                    json.getString("user_id"),
                    json.getString("register_title"),
                    json.getString("product_category"),
                    json.getString("product_subcategory"),
                    json.getString("product_type"),
                    json.getString("product_status"),
                    json.getString("product_brand"),
                    json.getString("product_price"),
                    json.getInt("seller_store"),
                    json.getString("register_content"),
                    json.getString("trade_option"),
                    json.getString("seller_address"),
                    json.getString("register_date"),
                    json.getInt("register_like"),
                    json.getInt("register_view"),
                    json.getString("user_nickname"),
                    ArrayList<Bitmap>())

                searchList.add(product)
            }
            rv_searchlist.setHasFixedSize(true)
            rv_searchlist.layoutManager=
                GridLayoutManager(this, 2)
            rv_searchlist.adapter= ProductAdapter(this, searchList)
        })
    }
}
