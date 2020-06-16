package com.honestyandpassion.ourcountry.MainActivity

import android.app.Dialog
import android.nfc.cardemulation.HostApduService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import com.honestyandpassion.ourcountry.Adapter.ChatAdapter
import com.honestyandpassion.ourcountry.Class.ToolbarSetting
import com.honestyandpassion.ourcountry.Class.UserInfo
import com.honestyandpassion.ourcountry.Item.ChatRoomItem
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_drawerlayout.*
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ChatActivity : AppCompatActivity()  {

    companion object {
        var HANDLER:Handler?=null
    }

    var chatAdapter = ChatAdapter()

    var room: ChatRoomItem? = null
    var roomId: String? = null
    var title: String? = null
    var ref: DatabaseReference? = null
    var query: Query? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
       // var toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_chat)


        setSupportActionBar(toolbar_chat)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        room=intent.getSerializableExtra("room") as ChatRoomItem

        roomId = room!!.roomId.toString()
        title = room!!.roomTitle
    //    toolbarBinding(toolbar, title.toString())
        //text_title.setText(title)
        supportActionBar?.setTitle(title.toString())

        FirebaseMessaging.getInstance().subscribeToTopic(roomId!!)
            .addOnCompleteListener {
                var msg = "${roomId} subscribe success"
                if (!it.isSuccessful) msg = "${roomId} subscribe fail"
            }

        setChatting()

        HANDLER=object : Handler(){
            override fun handleMessage(msg: Message?) {
                when(msg!!.what){
                    0 -> {
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

                        Log.d("test","${noon}")

                        objectMap.put("room_id", roomId!!)
                        objectMap.put("speaker", "OUR_COUNTRY_OPERATOR")
                        objectMap.put("content", "<우리 시골 알림>\n${UserInfo.NICKNAME}님의 결제가 완료되었습니다.")
                        objectMap.put("time", formatted)
                        objectMap.put("fulltime", fulltime)

                        root.updateChildren(objectMap)

                        VolleyService.sendFCMReq(
                            roomId!!,
                            title!!,
                            "${UserInfo.NICKNAME} : ${edit_chat.text}",
                            applicationContext
                        )
                    }
                }
            }
        }
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
          var inflater = getMenuInflater()
         inflater.inflate(R.menu.menu_chat, menu)

        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when (item.itemId) {
                android.R.id.home -> {
                    onBackPressed()
                    return true
                }
                R.id.nav_rating ->{
                    val dialog = Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar)
                    val dialogView=layoutInflater.inflate(R.layout.dialog_rating,null)

                    dialog.getWindow().getAttributes().windowAnimations = R.style.AnimationPopupStyle
                    dialog.addContentView(dialogView, ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT))
                    dialog.show()

                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun chatConversation(dataSnapshot: DataSnapshot) {
        Log.d("test","chatConversation")
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
