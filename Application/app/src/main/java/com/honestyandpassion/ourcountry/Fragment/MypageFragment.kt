package com.honestyandpassion.ourcountry.Fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.honestyandpassion.ourcountry.MainActivity.EditProfileActivity
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.fragment_mypage.*


class MypageFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         return inflater.inflate(R.layout.fragment_mypage, container, false)
        text_edit.setOnClickListener {
            var intent=Intent(context,EditProfileActivity::class.java)
            startActivity(intent)
        }
    }

}
