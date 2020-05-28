package com.honestyandpassion.ourcountry.IntroActivity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import kotlinx.android.synthetic.main.toolbar_layout.*
import android.R.menu
import android.R
import android.content.Context
import android.content.Intent
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.MenuItem
import androidx.core.view.GravityCompat
import com.honestyandpassion.ourcountry.Class.ToolbarSetting
import kotlinx.android.synthetic.main.activity_setting.*


class SettingActivity : ToolbarSetting() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.honestyandpassion.ourcountry.R.layout.activity_setting)

        var toolbar: androidx.appcompat.widget.Toolbar = findViewById(com.honestyandpassion.ourcountry.R.id.toolbar_setting)
        toolbarBinding(toolbar, "설정")

        text_logout.setOnClickListener {
            //VolleyService.removeToken(UserInfo.NICKNAME,context!!)

            var pref=getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
            var editor=pref.edit()

            editor.clear()
            editor.commit()

            var intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

}
