package com.honestyandpassion.ourcountry.MainActivity

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import com.honestyandpassion.ourcountry.Adapter.ChatAdapter
import com.honestyandpassion.ourcountry.Class.UserInfo
import com.honestyandpassion.ourcountry.Item.ChatRoomItem
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_chat.*
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ChatActivity : AppCompatActivity()  {

    companion object {
        var HANDLER:Handler?=null
        var registerId:Int?=null
    }

    var chatAdapter = ChatAdapter()

    var room: ChatRoomItem? = null
    var roomId: String? = null
    var title: String? = null
    var ref: DatabaseReference? = null
    var query: Query? = null
    var partner:String? = null
    //var registerId:String?=null
        //  var dialogMsg: DialogMsg? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
       // dialogMsg = DialogMsg(this)
       // var toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_chat)


        setSupportActionBar(toolbar_chat)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        room=intent.getSerializableExtra("room") as ChatRoomItem
        registerId= intent.getIntExtra("register_id",0)
        if(registerId==0) registerId=room!!.registerId

        Toast.makeText(this, registerId.toString(), Toast.LENGTH_SHORT).show()

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
                    val dialog2 = Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar)
                    val dialogView=layoutInflater.inflate(R.layout.dialog_rating,null)
                    val dialogReview=layoutInflater.inflate(R.layout.dialog_review_confirm,null)
                    val dialogConfirmBtn=dialogView.findViewById<Button>(R.id.btn_ratingaccept)
                    val dialogCancelBtn=dialogView.findViewById<Button>(R.id.btn_ratingcancel)
                    val ratingBar=dialogView.findViewById<RatingBar>(R.id.ratingBar)
                    var rating_value=0f
                    var reviewEdit=dialogReview.findViewById<EditText>(R.id.edit_review)
                    var reviewConfirmBtn=dialogReview.findViewById<Button>(R.id.btn_reviewaccept)
                    var reviewCancelBtn=dialogReview.findViewById<Button>(R.id.btn_reviewcancel)

                    dialog.getWindow().getAttributes().windowAnimations = R.style.AnimationPopupStyle
                    dialog.addContentView(dialogView, ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT))
                    dialog.show()

                    ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
                        rating_value=rating
                        Toast.makeText(this, rating_value.toString(), Toast.LENGTH_SHORT).show()
                    }

                    dialogCancelBtn.setOnClickListener{
                        dialog.dismiss()
                    }
                    dialogConfirmBtn.setOnClickListener {

                        if(UserInfo.ID!=room!!.maker){
                             partner = room!!.maker
                        }
                        else {
                            partner = room!!.partner
                        }
                        VolleyService.editRatingReq(partner!!,rating_value,this,{success-> })
                        dialog.dismiss()
                        dialog2.getWindow().getAttributes().windowAnimations = R.style.AnimationPopupStyle
                        dialog2.addContentView(dialogReview, ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT))
                        dialog2.show()
                        reviewCancelBtn.setOnClickListener {
                            dialog2.dismiss()
                        }
                        reviewConfirmBtn.setOnClickListener {
                            var intent = Intent(this, ReviewActivity::class.java)
                            intent.putExtra("registerId",registerId)
                            startActivity(intent)
                            dialog2.dismiss()
                        }


                    }


                    return true

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
