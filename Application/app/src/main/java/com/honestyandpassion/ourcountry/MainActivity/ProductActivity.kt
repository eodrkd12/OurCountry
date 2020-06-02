package com.honestyandpassion.ourcountry.MainActivity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.honestyandpassion.ourcountry.Adapter.ProductImagePagerAdapter
import com.honestyandpassion.ourcountry.Class.UserInfo
import com.honestyandpassion.ourcountry.Item.Product
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.R
import com.like.LikeButton
import kotlinx.android.synthetic.main.activity_product.*
import org.json.JSONObject
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import com.like.OnLikeListener
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View
import com.honestyandpassion.ourcountry.Item.ChatRoomItem


class ProductActivity : AppCompatActivity() {

    companion object{
        var imageList=ArrayList<Bitmap>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        btn_backpress.bringToFront()
        btn_backpress.setOnClickListener {
            finish()
        }
        var product=intent.getParcelableExtra<Product>("product")

        VolleyService.checkFollowReq(UserInfo.ID, product.userId!!, this, { success ->
            if (success!!.getInt("count") == 1) {
                btn_following.setText("팔로잉")
                btn_following.setBackgroundResource(R.drawable.rounded_following_button)
                btn_following.setTextColor(Color.parseColor("#212121"))
            }
        })

        btn_following.setOnClickListener {
            when(btn_following.text) {
                "팔로잉" -> {
                    VolleyService.deleteFollowReq(UserInfo.ID, product.userId!!, this, {success->
                        if(success=="success") {
                            btn_following.text = "+팔로우"
                            btn_following.setBackgroundResource(R.drawable.rounded_follow_button)
                            btn_following.setTextColor(Color.parseColor("#FFFFFF"))
                        }
                        else {
                            Toast.makeText(this, "서버와의 통신에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
                "+팔로우" -> {
                    VolleyService.insertFollowReq(UserInfo.ID, product.userId!!, this , { success->
                        if(success=="success") {
                            btn_following.text = "팔로잉"
                            btn_following.setBackgroundResource(R.drawable.rounded_following_button)
                            btn_following.setTextColor(Color.parseColor("#212121"))
                        }
                        else {
                            Toast.makeText(this, "서버와의 통신에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }


        if(product.userId == UserInfo.ID) {
            btn_following.visibility = View.INVISIBLE
            btn_chat.visibility = View.INVISIBLE
            btn_purchase.setText("수정")
            btn_purchase.setOnClickListener{
                var intent = Intent(this, RegisterActivity::class.java)
                intent.putExtra("registerType", "수정")
                intent.putExtra("product", product)
                startActivity(intent)
                finish()
            }
        }
        else {
            btn_chat.visibility = View.VISIBLE
            btn_purchase.setText("구매")
            btn_purchase.setOnClickListener {
                var intent = Intent(this, PaymentActivity::class.java)
                intent.putExtra("registerTitle", product!!.registerTitle)
                intent.putExtra("registerPrice", product!!.productPrice)
                startActivity(intent)
            }
        }

        pager_product_image.adapter=ProductImagePagerAdapter(this,imageList!!)

        text_productprice.setText(product!!.productPrice+"원")
        text_registertitle.setText(product!!.registerTitle)
        text_registerdate.setText(product!!.registerDate!!.substring(0,10))
        text_likecount.setText(product!!.registerLike.toString())
        text_viewcount.setText(product!!.registerView.toString())
        text_productstatus.setText(product!!.productStatus)
        var brand=product!!.productBrand
        if(brand=="")
            text_productbrand.setText("브랜드가 없습니다.")
        else
            text_productbrand.setText(brand)
        text_productcategory.setText("${product!!.productCategory}/${product!!.productSubCategory}")
        text_producttype.setText(product!!.productType)
        text_registercontent.setText(product!!.registerContent)
        text_productlocation.setText(product!!.sellerAddress)
        text_tradeoption.setText(product!!.tradeOption)

        VolleyService.myInfoReq(product!!.userId!!,this,{success ->
            var user:JSONObject=success!!

            text_sellernickname.setText(user.getString("user_nickname"))
            text_sellerratingaverage.setText(user.getDouble("user_rating_average").toString())
            rating_seller.rating=user.getDouble("user_rating_average").toFloat()
            text_ratingcount.setText("(${user.getInt("user_rating_count")})")
            var phone = user.getString("user_phone")
            if(phone=="")
                text_sellerphone.setText("등록된 번호가 없습니다.")
            else
                text_sellerphone.setText(phone)
            var joinDate = user.getString("user_join_date").format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            text_sellersignupdate.setText(joinDate.split('T')[0])
            var loginType=user.getString("user_login_type")
            when(loginType){
                "kakao" -> {
                    text_sellercertification.setText("카카오 인증 계정")
                }
                "email" -> {
                    text_sellercertification.setText("이메일 인증 계정")
                }
                "phone" -> {
                    text_sellercertification.setText("휴대폰 인증 계정")
                }
            }
        })

        btn_chat.setOnClickListener {
            var maker= UserInfo.ID
            var partner = product!!.userId
            val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
            val roomDate = current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

            VolleyService.createRoomReq(maker,partner,roomDate,product!!.registerTitle!!,this,{success ->
                var intent= Intent(this,ChatActivity::class.java)
                var room=ChatRoomItem(
                    success!!.getInt("room_id"),
                    success!!.getString("maker"),
                    success!!.getString("partner"),
                    success!!.getString("room_date"),
                    success!!.getString("room_title"),
                    null,null,null)
                intent.putExtra("room",room)
                startActivity(intent)
                finish()
            })
        }
        VolleyService.checkWishlistReq(product!!.registerId!!, UserInfo.ID, this, {success->
            if(success!!.getInt("count") == 1) {
                btn_favorite.setLiked(true)
            }
            else {
                btn_favorite.setLiked(false)
            }
        })

        btn_favorite.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton) {
                VolleyService.insertWishlistReq(product!!.registerId!!, UserInfo.ID, this@ProductActivity, {success->
                    if(success == "success")
                    {
                        likeButton.setLikeDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.heart_on, null))
                        Toast.makeText(this@ProductActivity, "위시리스트에 등록되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this@ProductActivity, "통신오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }
                })
            }

            override fun unLiked(likeButton: LikeButton) {
                VolleyService.deleteWishlistReq(product!!.registerId!!, UserInfo.ID, this@ProductActivity, {success->
                    if(success == "success")
                    {
                        likeButton.setLikeDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.heart_off, null))
                        Toast.makeText(this@ProductActivity, "위시리스트에서 제거되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this@ProductActivity, "통신오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        })

        VolleyService.increaseViewReq(product!!.registerId!!, this, {success-> // 조회수 증가
        })

        VolleyService.checkViewReq(UserInfo.ID, product!!.registerId!!, this, {success-> // 사용자기록 갱신
            if(success!!.getInt("count") == 1) {
                val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
                val viewDate =
                    current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                VolleyService.updateViewReq(UserInfo.ID, product!!.registerId!!, viewDate,this, {success->
                })
            }
            else if(success!!.getInt("count") == 0){
                val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
                val viewDate =
                    current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                VolleyService.insertViewReq(UserInfo.ID, product!!.registerId!!, viewDate, product!!.registerTitle!!,this, {success->
                })
            }
        })



    }
}