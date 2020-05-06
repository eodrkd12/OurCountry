package com.honestyandpassion.ourcountry.MainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.honestyandpassion.ourcountry.Fragment.CategoryFragment
import com.honestyandpassion.ourcountry.Fragment.HomeFragment
import com.honestyandpassion.ourcountry.Fragment.MessageFragment
import com.honestyandpassion.ourcountry.Fragment.MypageFragment
import com.honestyandpassion.ourcountry.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bnv_main)
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener)

        if (savedInstanceState == null) {
            val fragment = HomeFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_main, fragment, fragment.javaClass.simpleName).commit()
        }
    }

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.bnv_main_home -> {
                val fragment = HomeFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_main, fragment, fragment.javaClass.simpleName).commit()

                return@OnNavigationItemSelectedListener true
            }
            R.id.bnv_main_category -> {
                val fragment = CategoryFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_main, fragment, fragment.javaClass.simpleName).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.bnv_main_mypage -> {
                val fragment = MypageFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_main, fragment, fragment.javaClass.simpleName).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.bnv_main_message -> {
                val fragment = MessageFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_main, fragment, fragment.javaClass.simpleName).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}
