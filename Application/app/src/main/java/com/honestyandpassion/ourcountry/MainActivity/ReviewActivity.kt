package com.honestyandpassion.ourcountry.MainActivity

import android.os.Bundle
import com.honestyandpassion.ourcountry.Class.ToolbarSetting
import com.honestyandpassion.ourcountry.Class.UserInfo
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_review.*
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ReviewActivity:ToolbarSetting() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        var intent = intent
        var registerId:Int= intent.getIntExtra("registerId",0)

        var toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_review)
        toolbarBinding(toolbar, "리뷰등록")

        btn_save.setOnClickListener {

            var review_content=edit_review.text.toString()
            val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
            var date =current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

            VolleyService.editReviewReq(UserInfo.ID,registerId,review_content,date,this ,{success->
            })
            finish()
          }
        }
    }