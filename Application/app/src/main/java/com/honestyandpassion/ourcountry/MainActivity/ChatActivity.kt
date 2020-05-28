package com.honestyandpassion.ourcountry.MainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import com.honestyandpassion.ourcountry.Adapter.ChatAdapter
import com.honestyandpassion.ourcountry.Class.UserInfo
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_chat.*
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ChatActivity : AppCompatActivity() {

    var chatAdapter = ChatAdapter()
    var roomId: String? = null
    var title: String? = null
    var ref: DatabaseReference? = null
    var query: Query? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        var intent = intent

        roomId = intent.getIntExtra("room_id",0).toString()
        title = intent.getStringExtra("title")

        FirebaseMessaging.getInstance().subscribeToTopic(roomId!!)
            .addOnCompleteListener {
                var msg = "${roomId} subscribe success"
                if (!it.isSuccessful) msg = "${roomId} subscribe fail"
            }

        setChatting()
    }

    fun setChatting() {
        list_chat.adapter = chatAdapter

        ref = FirebaseDatabase.getInstance().reference.child("chat").child(roomId!!)
        query = ref!!.orderByChild("fulltime")

        val childEventListener = object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                chatConversation(p0)
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                chatConversation(p0)
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }
        }

        query!!.addChildEventListener(childEventListener)

        btn_send.setOnClickListener {
            if (edit_chat.text.toString() != "") {

                var map = HashMap<String, Any>()

                val key: String? = ref!!.push().key

                ref!!.updateChildren(map)

                var root = ref!!.child(key!!)
                var objectMap = HashMap<String, Any>()

                val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
                val noon = current.format(DateTimeFormatter.ofPattern("a"))
                var formatter: DateTimeFormatter? = null

                if (noon == "PM")
                    formatter = DateTimeFormatter.ofPattern("오후 hh:mm")
                else
                    formatter = DateTimeFormatter.ofPattern("오전 hh:mm")
                val formatted = current.format(formatter)
                val fulltime =
                    current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

                objectMap.put("room_id", roomId!!)
                objectMap.put("speaker", UserInfo.NICKNAME)
                objectMap.put("content", edit_chat.text.toString())
                objectMap.put("time", formatted)
                objectMap.put("fulltime", fulltime)

                root.updateChildren(objectMap)

                VolleyService.sendFCMReq(
                    roomId!!,
                    title!!,
                    "${UserInfo.NICKNAME} : ${edit_chat.text}",
                    this
                )

                edit_chat!!.setText("")
                list_chat.setSelection(chatAdapter.count - 1)
            }

        }

    }

    fun chatConversation(dataSnapshot: DataSnapshot) {
        var i = dataSnapshot.children.iterator()

        while (i.hasNext()) {

            var content = ((i.next() as DataSnapshot).getValue()) as String
            var fulltime = ((i.next() as DataSnapshot).getValue()) as String
            var roomId = ((i.next() as DataSnapshot).getValue()) as String
            var speaker = ((i.next() as DataSnapshot).getValue()) as String
            var time = ((i.next() as DataSnapshot).getValue()) as String

            chatAdapter.addItem(roomId, speaker, content, time, fulltime)
        }
        chatAdapter.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        finish()
    }
}
