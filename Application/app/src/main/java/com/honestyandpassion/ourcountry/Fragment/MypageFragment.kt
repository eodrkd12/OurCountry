package com.honestyandpassion.ourcountry.Fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.honestyandpassion.ourcountry.Class.UserInfo
import com.honestyandpassion.ourcountry.MainActivity.EditProfileActivity
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

        nameText.text=UserInfo.NICKNAME
        joinText.text=UserInfo.JOINDATE.substring(0,10)



        btnEdit.setOnClickListener {
            var intent = Intent(activity, EditProfileActivity::class.java)
            startActivity(intent)
        }
         return rootView

    }

}
