package com.honestyandpassion.ourcountry.Fragment

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.honestyandpassion.ourcountry.Adapter.ProductAdapter
import com.honestyandpassion.ourcountry.Class.UserInfo
import com.honestyandpassion.ourcountry.IntroActivity.SettingActivity
import com.honestyandpassion.ourcountry.Item.Product
import com.honestyandpassion.ourcountry.MainActivity.EditProfileActivity
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.MainActivity.ProductAllViewActivity
import com.honestyandpassion.ourcountry.R
import org.json.JSONObject


class MypageFragment : Fragment() {

    companion object{
        var ProductArrayList=ArrayList<Product>()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_mypage, container, false)
        var btnEdit : TextView = rootView.findViewById(R.id.text_edit)
        var nameText:TextView= rootView.findViewById((R.id.text_name))
        var joinText:TextView = rootView.findViewById(R.id.text_joindate)
        var settingBtn : ConstraintLayout = rootView.findViewById(R.id.layout_setting)
        var myListRV : RecyclerView =rootView.findViewById(R.id.rv_list)

    VolleyService.getMylistReq(UserInfo.ID,activity!!,{success->
        ProductArrayList.clear()
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

            ProductArrayList.add(product)
        }

        myListRV.setHasFixedSize(true)
        myListRV.layoutManager=LinearLayoutManager(activity!!,RecyclerView.HORIZONTAL,false)
        myListRV.adapter=ProductAdapter(activity!!,ProductArrayList)
    })
        var wishlistBtn : ConstraintLayout = rootView.findViewById(R.id.layout_wishlist)

        nameText.text=UserInfo.NICKNAME
        joinText.text=UserInfo.JOINDATE.substring(0,10)



        btnEdit.setOnClickListener {
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
