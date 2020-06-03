package com.honestyandpassion.ourcountry.MainActivity

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.honestyandpassion.ourcountry.Adapter.ProductAllViewAdapter
import com.honestyandpassion.ourcountry.Adapter.ProductPreviewAdapter
import com.honestyandpassion.ourcountry.Class.ToolbarSetting
import com.honestyandpassion.ourcountry.Class.UserInfo
import com.honestyandpassion.ourcountry.Item.PreviewItem
import com.honestyandpassion.ourcountry.Item.Product
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_product_all_view.*
import org.json.JSONObject

class ProductAllViewActivity : ToolbarSetting() {

    var wishList = ArrayList<Product>()
    var recentProductArrayList = ArrayList<PreviewItem>()
    var popularProductArrayList = ArrayList<PreviewItem>()
    var wishlistProductArrayList = ArrayList<PreviewItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_all_view)

        var intent = intent
        var clickedText:String = intent.getStringExtra("clickedText")

        var toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_recent_register)

        when(clickedText) {
            "최근등록된상품" -> {
                toolbarBinding(toolbar, "최근 등록된 상품")
                VolleyService.recentRegisterReq(this, { success->                //최근등록된상품
                    recentProductArrayList.clear()
                    var array = success
                    for(i in 0..array!!.length()-1) {
                        var json = array[i] as JSONObject
                        var recentProduct = PreviewItem(json.getInt("register_id"),
                            json.getString("register_title"),
                            json.getString("product_price"),
                            json.getString("product_status"),
                            ArrayList<Bitmap>())
                        recentProductArrayList.add(recentProduct)
                    }
                    activityBinding(recentProductArrayList)
                })
            }
            "인기상품" -> {
                toolbarBinding(toolbar, "인기상품")
                VolleyService.popularRegisterReq(this, { success->                //인기상품
                    popularProductArrayList.clear()
                    var array = success
                    for(i in 0..array!!.length()-1) {
                        var json = array[i] as JSONObject
                        var popularProduct = PreviewItem(json.getInt("register_id"),
                            json.getString("register_title"),
                            json.getString("product_price"),
                            json.getString("product_status"),
                            ArrayList<Bitmap>())
                        popularProductArrayList.add(popularProduct)
                    }
                    activityBinding(popularProductArrayList)
                })
            }
            "내가찜한상품" -> {
                toolbarBinding(toolbar, "내가 찜한 상품")
                VolleyService.getWishlistProductReq(UserInfo.ID, this, { success->
                    wishlistProductArrayList.clear()
                    var array = success
                    for(i in 0..array!!.length()-1) {
                        var json = array[i] as JSONObject
                        var wishlistProduct = PreviewItem(json.getInt("register_id"),
                            json.getString("register_title"),
                            json.getString("product_price"),
                            json.getString("product_status"),
                            ArrayList<Bitmap>())
                        wishlistProductArrayList.add(wishlistProduct)
                    }
                    activityBinding(wishlistProductArrayList)
                })
            }
        }

    }

    fun activityBinding(productList: ArrayList<PreviewItem>) {
        if(productList.size == 0) {
            image_emptyrecent.visibility = View.VISIBLE
            text_emptyrecent1.visibility = View.VISIBLE
            text_emptyrecent2.visibility = View.VISIBLE
        }
        else {
            rv_recent_register.setHasFixedSize(true)
            rv_recent_register.layoutManager = GridLayoutManager(this, 2)
            rv_recent_register.adapter = ProductAllViewAdapter(this, productList)
        }
    }
}
