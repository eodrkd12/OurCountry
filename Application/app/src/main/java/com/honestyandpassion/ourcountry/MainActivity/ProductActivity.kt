package com.honestyandpassion.ourcountry.MainActivity

import android.content.DialogInterface
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
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.honestyandpassion.ourcountry.Adapter.ProductPreviewAdapter
import com.honestyandpassion.ourcountry.Adapter.ReviewAdapter
import com.honestyandpassion.ourcountry.Class.DialogMsg
import com.honestyandpassion.ourcountry.Fragment.HomeFragment
import com.honestyandpassion.ourcountry.Fragment.MypageFragment
import com.honestyandpassion.ourcountry.Item.ChatRoomItem
import com.honestyandpassion.ourcountry.Item.PreviewItem
import com.honestyandpassion.ourcountry.Item.ReviewItem
import kotlinx.android.synthetic.main.activity_profile_edit.*


class ProductActivity : AppCompatActivity() {

    companion object{
        var imageList=ArrayList<Bitmap>()
        var reviewList=ArrayList<ReviewItem>()
    }

    var dialogMsg: DialogMsg? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        var intent = intent

        var registerId = intent.getIntExtra("register_id", 0)

        Toast.makeText(this, registerId.toString(), Toast.LENGTH_SHORT).show()

        btn_backpress.bringToFront()
        btn_backpress.setOnClickListener {
            finish()
        }


        VolleyService.getRegisterReq(registerId, this, {success->
            var json = success
            var registerId=json!!.getInt("register_id")
            var userId = json!!.getString("user_id")
            var registerTitle = json!!.getString("register_title")
            var productCategory = json!!.getString("product_category")
            var productSubCategory = json!!.getString("product_subcategory")
            var productType = json!!.getString("product_type")
            var productStatus = json!!.getString("product_status")
            var productBrand = json!!.getString("product_brand")
            var productPrice = json!!.getString("product_price")
            var sellerStore = json!!.getInt("seller_store")
            var registerContent = json!!.getString("register_content")
            var tradeOption = json!!.getString("trade_option")
            var sellerAddress = json!!.getString("seller_address")
            var registerDate = json!!.getString("register_date")
            var registerLike = json!!.getInt("register_like")
            var registerView = json!!.getInt("register_view")
            var userNickname = json!!.getString("user_nickname")
            var product=Product(registerId,userId,registerTitle,productCategory, productSubCategory, productType, productStatus,
                productBrand, productPrice, sellerStore, registerContent, tradeOption, sellerAddress,
                registerDate, registerLike, registerView, userNickname, ArrayList<Bitmap>())
            VolleyService.checkFollowReq(UserInfo.ID, userId, this, { success ->
                if (success!!.getInt("count") == 1) {
                    btn_following.setText("팔로잉")
                    btn_following.setBackgroundResource(R.drawable.rounded_following_button)
                    btn_following.setTextColor(Color.parseColor("#212121"))
                }


                btn_following.setOnClickListener {
                    when(btn_following.text) {
                        "팔로잉" -> {
                            VolleyService.deleteFollowReq(UserInfo.ID, userId, this, {success->
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
                            VolleyService.insertFollowReq(UserInfo.ID, userId, this , { success->
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
            })

        if(userId == UserInfo.ID) {
            btn_following.visibility = View.INVISIBLE
            btn_chat.visibility = View.INVISIBLE
            layout_favorite.visibility=View.INVISIBLE
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
                VolleyService.checkChatRoomReq(UserInfo.ID,product!!.userId,product!!.registerTitle,this,{success ->
                    when(success){
                        0 -> {
                            var builder=AlertDialog.Builder(this)
                            builder.setTitle("결제할 수 없습니다.")
                                .setMessage("판매자와 채팅을 먼저 하세요")
                                .setPositiveButton("확인",object : DialogInterface.OnClickListener{
                                    override fun onClick(dialog: DialogInterface?, which: Int) {

                                    }

                                })
                                .show()

                        }
                        1 -> {
                            var intent = Intent(this, PaymentActivity::class.java)
                            intent.putExtra("registerTitle", registerTitle)
                            intent.putExtra("registerPrice", productPrice)
                            intent.putExtra("seller",userId)
                            startActivity(intent)
                        }
                    }
                })
            }
        }

            pager_product_image.adapter=ProductImagePagerAdapter(this,imageList!!)

            text_productprice.setText(productPrice+"원")
            text_registertitle.setText(registerTitle)
            text_registerdate.setText(registerDate!!.substring(0,10))
            text_likecount.setText(registerLike.toString())
            text_viewcount.setText(registerView.toString())
            text_productstatus.setText(productStatus)
            var brand= productBrand
            if(brand=="")
                text_productbrand.setText("브랜드가 없습니다.")
            else
                text_productbrand.setText(brand)
            text_productcategory.setText("${productCategory}/${productSubCategory}")
            text_producttype.setText(productType)
            text_registercontent.setText(registerContent)
            text_productlocation.setText(sellerAddress)
            text_tradeoption.setText(tradeOption)


            VolleyService.myInfoReq(userId,this,{success ->
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
                var sellercertification =text_sellercertification.text
                layout_sellerinfo.setOnClickListener {
                    var intent=Intent(this,PageActivity::class.java)
                    intent.putExtra("name",userNickname)
                    intent.putExtra("id",userId)
                    intent.putExtra("user_rating_average",user.getDouble("user_rating_average").toString())
                    intent.putExtra("user_rating",user.getDouble("user_rating_average").toFloat())
                    intent.putExtra("user_rating_count","(${user.getInt("user_rating_count")})")
                    intent.putExtra("user_join_date",joinDate.split('T')[0])
                    intent.putExtra("sellercertification",sellercertification)
                    startActivity(intent)

                }
            })


            btn_chat.setOnClickListener {
                var maker= UserInfo.ID
                var partner = userId
                val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
                val roomDate = current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                VolleyService.checkChatRoomReq(maker,partner,registerTitle!!,this,{success ->
                    when(success){
                        0 -> {
                            VolleyService.createRoomReq(maker,partner,roomDate, registerTitle!!,registerId,this,{success ->
                                var intent= Intent(this,ChatActivity::class.java)
                                var room=ChatRoomItem(
                                    success!!.getInt("room_id"),
                                    success!!.getString("maker"),
                                    success!!.getString("partner"),
                                    success!!.getInt("register_id"),
                                    success!!.getString("room_date"),
                                    success!!.getString("room_title"),
                                    null,null,null)
                                intent.putExtra("room",room)
                                intent.putExtra("register_id",registerId)
                                startActivity(intent)
                                finish()
                            })
                        }
                        1 -> {
                            VolleyService.getRoomInfoReq(maker,partner,registerTitle!!,registerId, this,{success ->
                                var intent=Intent(this,ChatActivity::class.java)
                                var room=ChatRoomItem(
                                    success!!.getInt("room_id"),
                                    success!!.getString("maker"),
                                    success!!.getString("partner"),
                                    success!!.getInt("register_id"),
                                    success!!.getString("room_date"),
                                    success!!.getString("room_title"),
                                    null,null,null)
                                intent.putExtra("room",room)
                                intent.putExtra("register_id",registerId)
                                startActivity(intent)
                                finish()
                            })
                        }
                    }

                })
            }


            VolleyService.getReviewReq(registerId,this,{success->
                reviewList.clear()
                var array = success
                for(i in 0..array!!.length()-1) {
                    var json = array[i] as JSONObject
                    var reviewitem = ReviewItem(
                        json.getInt("review_id"),
                        json.getString("user_id"),
                        json.getString("review_content"),
                        json.getString("review_date"),
                        json.getInt("register_id")
                        )
                    reviewList.add(reviewitem)
                }
                rv_review.setHasFixedSize(true)
                rv_review.layoutManager=
                    LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                rv_review.adapter= ReviewAdapter(this,reviewList!! )

            })



            VolleyService.checkWishlistReq(registerId!!, UserInfo.ID, this, {success->
                if(success!!.getInt("count") == 1) {
                    btn_favorite.setLiked(true)
                }
                else {
                    btn_favorite.setLiked(false)
                }
            })

            btn_favorite.setOnLikeListener(object : OnLikeListener {
                override fun liked(likeButton: LikeButton) {
                    VolleyService.insertWishlistReq(registerId!!, UserInfo.ID, this@ProductActivity, {success->
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
                    VolleyService.deleteWishlistReq(registerId!!, UserInfo.ID, this@ProductActivity, {success->
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

            VolleyService.increaseViewReq(registerId!!, this, {success-> // 조회수 증가
            })


            VolleyService.checkViewReq(UserInfo.ID, registerId!!, this, {success-> // 사용자기록 갱신
                if(success!!.getInt("count") == 1) {
                    val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
                    val viewDate =
                        current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    VolleyService.updateViewReq(UserInfo.ID, registerId!!, viewDate,this, {success->
                    })
                }
                else if(success!!.getInt("count") == 0){
                    val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
                    val viewDate =
                        current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    VolleyService.insertViewReq(UserInfo.ID, registerId!!, viewDate, registerTitle!!,this, {success->
                    })
                }
            })
        })

    }
}