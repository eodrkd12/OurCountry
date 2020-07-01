package com.honestyandpassion.ourcountry.MainActivity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.fragment_mypage.*

class PageActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_mypage)
        var intent = intent
        var name=intent.getStringExtra("name")
        var user_rating_average=intent.getStringExtra("user_rating_average")
        var user_rating = intent.getFloatExtra("user_rating",0.0f)
        var user_rating_count = intent.getStringExtra("user_rating_count")
        var user_join_date = intent.getStringExtra("user_join_date")
        var sellercertification = intent.getStringExtra("sellercertification")

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
    }

}