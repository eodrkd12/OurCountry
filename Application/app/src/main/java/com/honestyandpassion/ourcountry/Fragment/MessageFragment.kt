package com.honestyandpassion.ourcountry.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.honestyandpassion.ourcountry.Adapter.ChatRoomAdapter
import com.honestyandpassion.ourcountry.Class.UserInfo
import com.honestyandpassion.ourcountry.Item.ChatRoomItem
import com.honestyandpassion.ourcountry.Object.VolleyService

import com.honestyandpassion.ourcountry.R
import org.json.JSONObject


class MessageFragment : Fragment() {

    var chatRoomList:ArrayList<ChatRoomItem>?=null
    var chatRoomAdapter:ChatRoomAdapter?=null
    init{
        chatRoomList=ArrayList<ChatRoomItem>()
    }

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

                val ref = FirebaseDatabase.getInstance().reference.child("chat").child(json.getInt("room_id").toString())
                val query = ref.orderByChild("fulltime").limitToLast(1)

                val childEventListener = object : ChildEventListener {
                    override fun onCancelled(p0: DatabaseError) {}
                    override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
                    override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                        chatConversation(p0)
                    }
                    override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                        chatConversation(p0)
                    }
                    override fun onChildRemoved(p0: DataSnapshot) {}
                }

                query.addChildEventListener(childEventListener)

                var lastChat = ""
                var lastChatTime = ""

                chatRoomList!!.add(ChatRoomItem(
                    json.getInt("room_id"),
                    json.getString("maker"),
                    json.getString("partner"),
                    json.getString("room_date"),
                    json.getString("room_title"),
                    lastChat,
                    lastChatTime,
                ""))
            }



            chatRoomRV.setHasFixedSize(true)
            chatRoomRV.layoutManager = LinearLayoutManager(activity!!, RecyclerView.VERTICAL, false)
            chatRoomAdapter=ChatRoomAdapter(activity!!, chatRoomList!!)
            chatRoomRV.adapter = chatRoomAdapter
        })



        return rootView
    }

    fun chatConversation(dataSnapshot: DataSnapshot) {
        var i = dataSnapshot.children.iterator()

        var content = ((i.next() as DataSnapshot).getValue()) as String
        var fulltime = ((i.next() as DataSnapshot).getValue()) as String
        var roomId = ((i.next() as DataSnapshot).getValue()) as String
        var speaker=((i.next() as DataSnapshot).getValue()) as String
        var time=((i.next() as DataSnapshot).getValue()) as String
        chatRoomAdapter!!.insertLastChat(roomId,content,fulltime,time)
        chatRoomAdapter!!.sortByLastChat()
        chatRoomAdapter!!.notifyDataSetChanged()
    }
}
