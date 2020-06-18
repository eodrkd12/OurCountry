package com.honestyandpassion.ourcountry.MainActivity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.honestyandpassion.ourcountry.Class.AlarmSetting
import com.honestyandpassion.ourcountry.Class.ToolbarSetting
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_alarm_setting.*

class AlarmSettingActivity : ToolbarSetting() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_setting)

        var toolbar: androidx.appcompat.widget.Toolbar = findViewById(com.honestyandpassion.ourcountry.R.id.toolbar_alarmsetting)
        toolbarBinding(toolbar, "알람 설정")

        switch_alarmsetting.setOnCheckedChangeListener { buttonView, isChecked ->
            var alarmPref=this.getSharedPreferences("Alarm", Context.MODE_PRIVATE)
            var editor=alarmPref.edit()
            editor.clear().apply()
            if(isChecked){
                editor.putBoolean("alarm",true)
                AlarmSetting.alarm=true
            }
            else{
                editor.putBoolean("alarm",false)
                AlarmSetting.alarm=false
            }
            editor.commit()
        }
    }
}