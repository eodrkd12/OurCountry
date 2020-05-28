package com.honestyandpassion.ourcountry.Fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Layout
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Constraints
import com.honestyandpassion.ourcountry.Class.UserInfo
import com.honestyandpassion.ourcountry.IntroActivity.SettingActivity
import com.honestyandpassion.ourcountry.MainActivity.EditProfileActivity
import com.honestyandpassion.ourcountry.MainActivity.ProductAllViewActivity
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.fragment_mypage.*


class MypageFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_mypage, container, false)
        var btnEdit : TextView = rootView.findViewById(R.id.text_edit)
        var nameText:TextView= rootView.findViewById((R.id.text_name))
        var joinText:TextView = rootView.findViewById(R.id.text_joindate)
        var settingBtn : ConstraintLayout = rootView.findViewById(R.id.layout_setting)
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
