package com.honestyandpassion.ourcountry.IntroActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_join1.*

class Join1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join1)

        btn_next.setOnClickListener {

            var email=edit_email.text.toString()
            var nickname=edit_nickname.text.toString()
            var password=edit_password.text.toString()

            VolleyService.emailCheckReq(email,this,{success ->
                var intent= Intent(this,Join2Activity::class.java)
                intent.putExtra("eamil",email)
                intent.putExtra("nickname",nickname)
                intent.putExtra("password",password)
            })
        }
    }
}
