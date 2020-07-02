package com.honestyandpassion.ourcountry.Fragment

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.honestyandpassion.ourcountry.Adapter.ProductPreviewAdapter
import com.honestyandpassion.ourcountry.Class.UserInfo
import com.honestyandpassion.ourcountry.IntroActivity.SettingActivity
import com.honestyandpassion.ourcountry.Item.PreviewItem
import com.honestyandpassion.ourcountry.MainActivity.EditProfileActivity
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.MainActivity.ProductAllViewActivity
import com.honestyandpassion.ourcountry.MainActivity.RefundActivity
import com.honestyandpassion.ourcountry.R
import org.json.JSONObject


class MypageFragment : Fragment() {

    companion object{
        var productArrayList=ArrayList<PreviewItem>()
        var pointText:TextView?=null
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_mypage, container, false)
        var editBtn : TextView = rootView.findViewById(R.id.text_edit)
        var nameText:TextView= rootView.findViewById((R.id.text_name))
        var joinText:TextView = rootView.findViewById(R.id.text_joindate)
        var settingBtn : ConstraintLayout = rootView.findViewById(R.id.layout_setting)
        var myProductRV : RecyclerView =rootView.findViewById(R.id.rv_list)
        var followerText : TextView = rootView.findViewById(R.id.text_follower)
        var followingText : TextView = rootView.findViewById(R.id.text_following)
        var wishlistBtn : ConstraintLayout = rootView.findViewById(R.id.layout_wishlist)
        pointText=rootView.findViewById(R.id.text_point)
        var refundImg:ImageView=rootView.findViewById(R.id.img_refund)

        pointText!!.setText("포인트 ${UserInfo.POINT}")

        VolleyService.getPoint(UserInfo.ID,activity!!,{success ->
            var json=success
            if(UserInfo.POINT!=json.getInt("user_point")){
                UserInfo.POINT=json.getInt("user_point")
                pointText!!.setText("포인트 ${UserInfo.POINT}")
            }
        })

        refundImg.setOnClickListener {
            var intent = Intent(activity, RefundActivity::class.java)
            startActivity(intent)

        }

        VolleyService.getCountFollowerReq(UserInfo.ID, activity!!, {success->
            followerText.setText("팔로워(${success!!.getInt("count").toString()})")
        })

        VolleyService.getCountFollowingReq(UserInfo.ID, activity!!, {success->
            followingText.setText("팔로잉(${success!!.getInt("count").toString()})")
        })

        VolleyService.getMyProductReq(UserInfo.ID, activity!!, {success->
            productArrayList.clear()
            var array = success
            for(i in 0..array!!.length()-1) {
                var json = array[i] as JSONObject
                var recentProduct = PreviewItem(json.getInt("register_id"),
                    json.getString("register_title"),
                    json.getString("product_price"),
                    json.getString("product_status"),
                    ArrayList<Bitmap>())
                productArrayList.add(recentProduct)
            }
            myProductRV.setHasFixedSize(true)
            myProductRV.layoutManager=LinearLayoutManager(activity!!, RecyclerView.VERTICAL, false)
            myProductRV.adapter= ProductPreviewAdapter(activity!!, productArrayList)
        })

        nameText.text=UserInfo.NICKNAME
        joinText.text=UserInfo.JOINDATE.substring(0,10)

        var textVerified=rootView.findViewById<TextView>(R.id.text_verified)

        when(UserInfo.LOGIN_TYPE){
            "kakao" -> {
                textVerified.setText("카카오 인증 계정")
            }
            "email" -> {
                textVerified.setText("이메일 인증 계정")
            }
            "phone" -> {
                textVerified.setText("휴대폰 인증 계정")
            }
        }

        editBtn.setOnClickListener {
            var intent = Intent(activity, EditProfileActivity::class.java)
            startActivity(intent)
        }

        settingBtn.setOnClickListener {
            var intent = Intent(activity, SettingActivity::class.java)
            startActivity(intent)
        }

        wishlistBtn.setOnClickListener {
            var intent = Intent(activity, ProductAllViewActivity::class.java)
            intent.putExtra("clickedText", "내가찜한상품")
            startActivity(intent)
        }

        return rootView

    }
}
