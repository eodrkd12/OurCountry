package com.honestyandpassion.ourcountry.Fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.honestyandpassion.ourcountry.Class.UserInfo
import com.honestyandpassion.ourcountry.Item.ChatRoomItem
import com.honestyandpassion.ourcountry.Item.Product
import com.honestyandpassion.ourcountry.Object.VolleyService

import com.honestyandpassion.ourcountry.R


class MessageFragment : Fragment() {

    var chatRoomArrayList=ArrayList<ChatRoomItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        VolleyService.getMyChatRoomReq(UserInfo.ID,activity!!,{success ->

        })

        return inflater.inflate(R.layout.fragment_message, container, false)
    }

}
