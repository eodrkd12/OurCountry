package com.honestyandpassion.ourcountry.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.honestyandpassion.ourcountry.Adapter.ChatRoomAdapter
import com.honestyandpassion.ourcountry.Class.UserInfo
import com.honestyandpassion.ourcountry.Item.ChatRoomItem
import com.honestyandpassion.ourcountry.Object.VolleyService

import com.honestyandpassion.ourcountry.R
import org.json.JSONObject


class MessageFragment : Fragment() {

    var chatRoomList=ArrayList<ChatRoomItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_message, container, false)
        var chatRoomRV=rootView.findViewById<RecyclerView>(R.id.rv_chat_room)



        VolleyService.getMyChatRoomReq(UserInfo.ID,activity!!,{success ->
            var array=success

            for(i in 0..array.length()-1){
                var json=array[i] as JSONObject

                chatRoomList.add(ChatRoomItem(
                    json.getInt("room_id"),
                    json.getString("maker"),
                    json.getString("partner"),
                    json.getString("room_date"),
                    json.getString("room_title")))
            }

            chatRoomRV.setHasFixedSize(true)
            chatRoomRV.layoutManager = LinearLayoutManager(activity!!, RecyclerView.HORIZONTAL, false)
            chatRoomRV.adapter = ChatRoomAdapter(activity!!, chatRoomList)
        })



        return rootView
    }

}
