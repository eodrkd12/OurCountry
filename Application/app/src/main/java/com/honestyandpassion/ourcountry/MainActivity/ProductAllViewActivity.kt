package com.honestyandpassion.ourcountry.MainActivity

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.honestyandpassion.ourcountry.Adapter.SearchProductAdapter
import com.honestyandpassion.ourcountry.Class.ToolbarSetting
import com.honestyandpassion.ourcountry.Class.UserInfo
import com.honestyandpassion.ourcountry.Fragment.HomeFragment
import com.honestyandpassion.ourcountry.Item.Product
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_product_all_view.*
import org.json.JSONObject

class ProductAllViewActivity : ToolbarSetting() {

    var wishList = ArrayList<Product>()

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
        else if(clickedText == "내가찜한상품") {
            toolbarBinding(toolbar, "내가찜한상품")
            VolleyService.getWishlistProductReq(UserInfo.ID, this, {success->
                wishList.clear()
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
                    wishList.add(product)
                }
                activityBinding(wishList)
            })
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
