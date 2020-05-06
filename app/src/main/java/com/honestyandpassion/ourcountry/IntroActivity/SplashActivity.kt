package com.honestyandpassion.ourcountry.IntroActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.honestyandpassion.ourcountry.MainActivity.MainActivity
import com.honestyandpassion.ourcountry.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        var intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
