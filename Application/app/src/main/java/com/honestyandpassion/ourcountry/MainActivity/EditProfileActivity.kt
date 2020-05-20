package com.honestyandpassion.ourcountry.MainActivity

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.honestyandpassion.ourcountry.Class.UserInfo
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_profile_edit.*
import kr.co.bootpay.Bootpay.finish

class EditProfileActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)
      //  setSupportActionBar(toolbar)
      //  supportActionBar?.setDisplayHomeAsUpEnabled(true)

        edit_nickname.setText(UserInfo.NICKNAME)
        edit_email.setText(UserInfo.ID)
        edit_phone.setText(UserInfo.PHONE)
        edit_address.setText(UserInfo.ADDRESS)
        edit_about.setText(UserInfo.ABOUT)

        btn_save.setOnClickListener {
            var id = edit_email.text.toString()
            var phone = edit_phone.text.toString()
            var nickname=edit_nickname.text.toString()
          //  var city= edit_city.text.toString()
            var about =edit_about.text.toString()
            var address = edit_address.text.toString()

        VolleyService.editUserReq(id,nickname,phone,about,address,this,{ success->

            UserInfo.NICKNAME=nickname
            UserInfo.PHONE=phone
            UserInfo.ADDRESS=address
            UserInfo.ABOUT=about

            var pref=this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
            var editor=pref.edit()
            editor
                .putString("NICKNAME", UserInfo.NICKNAME)
                .putString("PHONE", UserInfo.PHONE)
                .putString("ADDRESS", UserInfo.ADDRESS)
                .putString("ABOUT", UserInfo.ABOUT)
                .apply()

            edit_nickname.setText(UserInfo.NICKNAME)
            edit_email.setText(UserInfo.ID)
            edit_phone.setText(UserInfo.PHONE)
            edit_address.setText(UserInfo.ADDRESS)
            edit_about.setText(UserInfo.ABOUT)

            Toast.makeText(this, "내 정보가 변경되었습니다.",Toast.LENGTH_SHORT).show()

        })

        }


        }

/*override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item!!.itemId) {
        android.R.id.home -> {
            finish()
        }
    }
    return false
    }*/
}