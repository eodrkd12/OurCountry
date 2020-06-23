package com.honestyandpassion.ourcountry.Item

import java.io.Serializable

class ReviewItem (
    val review_id:Int?,
    val user_id:String?,
    val review_content:String?,
    val review_date:String?,
    val register_id:Int?

):Serializable