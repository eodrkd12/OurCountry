package com.honestyandpassion.ourcountry.MainActivity

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.honestyandpassion.ourcountry.Class.ToolbarSetting
import com.honestyandpassion.ourcountry.Class.UserInfo
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_profile_edit.*


class ChangePasswordActivity:  ToolbarSetting()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        var toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar3)
        toolbarBinding(toolbar, "비밀번호변경")


        edit_check.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(edit_check.text.toString().equals(edit_password.text.toString())){
                    text_check_alarm.setText(" ")
                    var password = edit_check.text.toString()
                }
                else   text_check_alarm.setText("비밀번호가 일치하지 않습니다.")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                text_check_alarm.setText(" ")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
              if(edit_check.text.toString().equals(edit_password.text.toString())){
                  text_check_alarm.setText(" ")
              }
                else   text_check_alarm.setText("비밀번호가 일치하지 않습니다.")
            }

        })


        btn_password_save.setOnClickListener {
            var id = UserInfo.ID
            var password = edit_check.text.toString()

            if(edit_check.text.toString().equals(edit_password.text.toString())) {

                if(edit_check.text.toString().length<5)
                    Toast.makeText(this, "5 이상 글자수를 입력해주세요.", Toast.LENGTH_SHORT).show()
                else {
                    VolleyService.editPasswordReq(
                        id,
                        password,
                        this,
                        { success ->

                            UserInfo.PW = password


                            var pref = this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                            var editor = pref.edit()
                            editor
                                .putString("id", UserInfo.ID)
                                .putString("password", UserInfo.PW)
                                .apply()

                            Toast.makeText(this, "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show()
                            finish()

                        })
                }
            }
            else
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
        }

    }
}