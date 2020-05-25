package com.honestyandpassion.ourcountry.MainActivity

import android.graphics.Bitmap
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



class ProductActivity : AppCompatActivity() {

    companion object{
        var imageList=ArrayList<Bitmap>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        var product=intent.getParcelableExtra<Product>("product")

        pager_product_image.adapter=ProductImagePagerAdapter(this,imageList!!)

        text_productprice.setText(product.productPrice+"원")
        text_registertitle.setText(product.registerTitle)
        var registerDate = product.registerDate!!.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        text_registerdate.setText(registerDate.split('T')[0])
        text_likecount.setText(product.registerLike.toString())
        text_viewcount.setText(product.registerView.toString())
        text_productstatus.setText(product.productStatus)
        var brand=product.productBrand
        if(brand=="")
            text_productbrand.setText("브랜드가 없습니다.")
        else
            text_productbrand.setText(brand)
        text_productcategory.setText("${product.productCategory}/${product.productSubCategory}")
        text_producttype.setText(product.productType)
        text_registercontent.setText(product.registerContent)
        text_productlocation.setText(product.sellerAddress)
        text_tradeoption.setText(product.tradeOption)

        VolleyService.myInfoReq(product.userId!!,this,{success ->
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

        VolleyService.checkWishlistReq(product.registerId!!, UserInfo.ID, this, {success->
            if(success!!.getInt("count") == 1) {
                btn_favorite.setLiked(true)
            }
            else {
                btn_favorite.setLiked(false)
            }
        })

        btn_favorite.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton) {
                VolleyService.insertWishlistReq(product.registerId!!, UserInfo.ID, this@ProductActivity, {success->
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
                VolleyService.deleteWishlistReq(product.registerId!!, UserInfo.ID, this@ProductActivity, {success->
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
    }
}
