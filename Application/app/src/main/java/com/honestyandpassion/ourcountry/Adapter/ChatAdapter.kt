package com.honestyandpassion.ourcountry.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.honestyandpassion.ourcountry.Class.UserInfo
import com.honestyandpassion.ourcountry.Item.ChatItem
import com.honestyandpassion.ourcountry.R


class ChatAdapter : BaseAdapter() {
    private var chatList = ArrayList<ChatItem>()
    override fun getCount(): Int {
        return chatList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return chatList.get(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val context: Context? = parent?.context

        var item = chatList[position]

        val inflater =
            context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        if(item.speaker=="OUR_COUNTRY_OPERATOR"){
            view = inflater.inflate(R.layout.item_operator_chat, parent, false)

            var textContent = view!!.findViewById(R.id.text_content) as TextView
            var textTime = view.findViewById(R.id.text_time) as TextView

            textContent.text = item.content
            textTime.text = item.time
        }
        else {
            if (!item.isMyChat!!) {
                view = inflater.inflate(R.layout.item_chat, parent, false)
                var textSpeaker = view!!.findViewById(R.id.text_speaker) as TextView
                textSpeaker!!.text = item.speaker
                var textContent = view!!.findViewById(R.id.text_content) as TextView
                var textTime = view.findViewById(R.id.text_time) as TextView

                textContent.text = item.content
                textTime.text = item.time
            } else {
                view = inflater.inflate(R.layout.item_my_chat, parent, false)

                var textContent = view!!.findViewById(R.id.text_content) as TextView
                var textTime = view.findViewById(R.id.text_time) as TextView

                textContent.text = item.content
                textTime.text = item.time
            }
        }
        //}



        return view
    }

    fun addItem(
        roomId: String, speaker: String, content: String, time: String, fulltime: String
    ) {
        var item = ChatItem(roomId,speaker,content,time,fulltime,null)

        if (speaker == UserInfo.NICKNAME)
            item.isMyChat = true
        else
            item.isMyChat = false

        item.roomId = roomId
        item.speaker = speaker
        item.content = content
        item.time = time
        item.fulltime = fulltime

        chatList.add(item)
    }

    fun clear() {
        chatList.clear()
    }
}
