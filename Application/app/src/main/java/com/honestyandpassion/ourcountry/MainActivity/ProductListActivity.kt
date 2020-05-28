package com.honestyandpassion.ourcountry.MainActivity

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.honestyandpassion.ourcountry.Adapter.SearchProductAdapter
import com.honestyandpassion.ourcountry.Class.ToolbarSetting
import com.honestyandpassion.ourcountry.Item.Product
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_product_list.*
import org.json.JSONObject

class ProductListActivity : ToolbarSetting() {

    companion object{
        var productList = ArrayList<Product>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        var intent = intent
        var subCategoryType: String = intent.getStringExtra("subCategoryType")

        var toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_productlist)
        toolbarBinding(toolbar, subCategoryType)

        VolleyService.getSubCategoryProductReq(subCategoryType, this, {success->
            productList.clear()
            var array = success
            for(i in 0..array.length()-1) {
                var json = array[i] as JSONObject
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

                productList.add(product)
            }
            activityBinding(productList)
        })
    }

    fun activityBinding(productList: ArrayList<Product>) {
        if(productList.size == 0) {
            image_emptyrecent.visibility = View.VISIBLE
            text_emptyrecent1.visibility = View.VISIBLE
            text_emptyrecent2.visibility = View.VISIBLE
        }
        else {
            rv_productlist.setHasFixedSize(true)
            rv_productlist.layoutManager = GridLayoutManager(this, 2)
            rv_productlist.adapter = SearchProductAdapter(this, productList)
        }
    }

}
