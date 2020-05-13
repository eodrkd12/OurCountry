package com.honestyandpassion.ourcountry.IntroActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_join3.*

class Join3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join3)

        var email=intent.getStringExtra("email")
        var nickname=intent.getStringExtra("nickname")
        var password=intent.getStringExtra("password")

        var intent= Intent(this,Join4Activity::class.java)
        intent.putExtra("email",email)
        intent.putExtra("nickname",nickname)
        intent.putExtra("password",password)

        btn_buyer.setOnClickListener {
            intent.putExtra("user_type","buyer")
            startActivity(intent)
        }
        btn_seller.setOnClickListener {
            intent.putExtra("user_type","seller")
            startActivity(intent)
        }
    }
}
