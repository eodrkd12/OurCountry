package com.honestyandpassion.ourcountry.MainActivity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.honestyandpassion.ourcountry.Class.ToolbarSetting
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_change_password.*


class ChangePasswordActivity:  ToolbarSetting()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        var toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar3)
        toolbarBinding(toolbar, "비밀번호변경")

//        edit_check.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//                if(edit_check.text==edit_password.text){
//                    text_check_alarm.setText("비밀번호가 일치하지 않습니다.")
//                }
//                else   text_check_alarm.setText(" ")
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//              if(edit_check.text==edit_password.text){
//                  text_check_alarm.setText("비밀번호가 일치하지 않습니다.")
//              }
//                else   text_check_alarm.setText(" ")
//            }
//
//        })

    }
}