package com.honestyandpassion.ourcountry.MainActivity

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.honestyandpassion.ourcountry.Class.ToolbarSetting
import com.honestyandpassion.ourcountry.Class.UserInfo
import com.honestyandpassion.ourcountry.Fragment.MypageFragment
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_refund.*
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class RefundActivity: ToolbarSetting() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refund)


        btn_refund.setOnClickListener {
            if(edit_refund.text.toString()==""){
                Toast.makeText(this,"환급할 포인트를 입력해주세요.",Toast.LENGTH_LONG).show()
            }
            else{
                var point=edit_refund.text.toString().toInt()
                if(UserInfo.POINT<point){
                    Toast.makeText(this,"환급할 포인트가 너무 많습니다.",Toast.LENGTH_LONG).show()
                }
                else {
                    val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
                    val refund_date =
                        current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    VolleyService.refundReq(
                        UserInfo.ID,
                        refund_date,
                        point,
                        UserInfo.BANK,
                        UserInfo.ACCOUNT,
                        this,
                        { success ->
                            UserInfo.POINT -= point
                            MypageFragment.pointText!!.text="포인트 ${UserInfo.POINT}"

                            var pref=this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                            var editor=pref.edit()
                            editor.remove("POINT").apply()
                            editor.putInt("POINT",UserInfo.POINT).apply()

                            finish()
                        })
                }
            }
        }
    }
}