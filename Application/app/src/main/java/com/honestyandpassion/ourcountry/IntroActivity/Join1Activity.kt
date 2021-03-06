package com.honestyandpassion.ourcountry.IntroActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
            var password=edit_review.text.toString()

            VolleyService.emailCheckReq(email,this,{success ->
                when(success){
                    "success" -> {
                        var intent= Intent(this,Join2Activity::class.java)
                        intent.putExtra("email",email)
                        intent.putExtra("nickname",nickname)
                        intent.putExtra("password",password)
                        startActivity(intent)
                    }
                    "fail" -> {
                        Toast.makeText(this,"중복된 이메일입니다.",Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }

        text_back.setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
