package com.honestyandpassion.ourcountry.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.honestyandpassion.ourcountry.Class.UserInfo
import com.honestyandpassion.ourcountry.Item.ChatRoomItem
import com.honestyandpassion.ourcountry.MainActivity.ChatActivity
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.item_chat_room.view.*

class ChatRoomAdapter(val context: Context, val chatRoomList: ArrayList<ChatRoomItem>) :
    RecyclerView.Adapter<ChatRoomAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return chatRoomList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomAdapter.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat_room, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChatRoomAdapter.ViewHolder, position: Int) {
        var chatRoom = chatRoomList[position]
        holder.itemView.text_title.setText(chatRoom.roomTitle)
        holder.itemView.text_last_chat.text = "${chatRoom.lastChat}"
        holder.itemView.text_last_chat_time.text = "${chatRoom.lastChatTime}"

        holder.view.setOnClickListener {
            var intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("room", chatRoom)
            startActivity(context, intent, null)
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindItems(data: String) {

        }
    }

    fun insertLastChat(roomId: String, content: String, fulltime: String, time: String) {
        for (i in 0..chatRoomList.size - 1) {
            if (chatRoomList[i].roomId.toString() == roomId) {
                chatRoomList[i].lastChat = content
                chatRoomList[i].lastChatFullTime = fulltime
                chatRoomList[i].lastChatTime = time
                break
            }
        }
    }

    fun sortByLastChat() {
        chatRoomList.sortByDescending { selector(it) }
    }

    fun selector(room: ChatRoomItem): String = room.lastChatFullTime!!
}