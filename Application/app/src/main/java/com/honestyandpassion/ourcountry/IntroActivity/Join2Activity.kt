package com.honestyandpassion.ourcountry.IntroActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.common.api.Scope
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_join2.*

class Join2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join2)

        var email=intent.getStringExtra("email")
        var nickname=intent.getStringExtra("nickname")
        var password=intent.getStringExtra("password")

        btn_next.setOnClickListener {

        }
    }

    fun sendMail(email:String){

    }
}
