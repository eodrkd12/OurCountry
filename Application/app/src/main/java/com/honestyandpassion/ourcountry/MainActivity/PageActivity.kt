package com.honestyandpassion.ourcountry.MainActivity

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.honestyandpassion.ourcountry.Adapter.ProductPreviewAdapter
import com.honestyandpassion.ourcountry.Class.UserInfo
import com.honestyandpassion.ourcountry.Fragment.MypageFragment
import com.honestyandpassion.ourcountry.Item.PreviewItem
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.fragment_mypage.*
import org.json.JSONObject

class PageActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_mypage)
        var intent = intent
        var name=intent.getStringExtra("name")
        var id=intent.getStringExtra("id")
        var user_rating_average=intent.getStringExtra("user_rating_average")
        var user_rating = intent.getFloatExtra("user_rating",0.0f)
        var user_rating_count = intent.getStringExtra("user_rating_count")
        var user_join_date = intent.getStringExtra("user_join_date")
        var sellercertification = intent.getStringExtra("sellercertification")

        var myProductRV : RecyclerView =findViewById(R.id.rv_list)


        text_name.setText(name)
        ratingstar.rating=user_rating
        text_count.setText(user_rating_average)
        text_allrating.setText(user_rating_count)
        text_joindate.setText(user_join_date)
        text_verified.setText(sellercertification)


        layout_wishlist.setVisibility(View.GONE)
        layout_setting.setVisibility(View.GONE)
        view12.setVisibility(View.GONE)
        view13.setVisibility(View.GONE)
        text_point.setVisibility(View.GONE)
        img_refund.setVisibility(View.GONE)
        text_edit.setVisibility(View.GONE)

        VolleyService.getCountFollowerReq(id, this!!, { success->
            text_follower.setText("팔로워(${success!!.getInt("count").toString()})")
        })

        VolleyService.getCountFollowingReq(id, this!!, { success->
            text_following.setText("팔로잉(${success!!.getInt("count").toString()})")
        })


        VolleyService.getMyProductReq(id, this!!, {success->
            MypageFragment.productArrayList.clear()
            var array = success
            for(i in 0..array!!.length()-1) {
                var json = array[i] as JSONObject
                var recentProduct = PreviewItem(json.getInt("register_id"),
                    json.getString("register_title"),
                    json.getString("product_price"),
                    json.getString("product_status"),
                    ArrayList<Bitmap>())
                MypageFragment.productArrayList.add(recentProduct)
            }
            myProductRV.setHasFixedSize(true)
            myProductRV.layoutManager=
                LinearLayoutManager(this!!, RecyclerView.VERTICAL, false)
            myProductRV.adapter= ProductPreviewAdapter(this!!, MypageFragment.productArrayList)
        })
    }

}