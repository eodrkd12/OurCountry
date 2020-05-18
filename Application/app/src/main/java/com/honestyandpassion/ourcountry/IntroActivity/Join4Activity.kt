package com.honestyandpassion.ourcountry.IntroActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.honestyandpassion.ourcountry.Adapter.BankAdapter
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_join4.*

class Join4Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join4)

        var email=intent.getStringExtra("email")
        var nickname=intent.getStringExtra("nickname")
        var password=intent.getStringExtra("password")
        var userType=intent.getStringExtra("user_type")
        var image=intent.getStringExtra("image")
        var phone=intent.getStringExtra("phone")
        var loginType=intent.getStringExtra("login_type")

        var bankList=resources.getStringArray(R.array.array_bank)

        var bankAdapter=BankAdapter(this,bankList)
        grid_bank.adapter=bankAdapter
        edit_bank.setOnClickListener {
            grid_bank.bringToFront()
            grid_bank.visibility= View.VISIBLE
            btn_next.visibility=View.GONE
        }

        grid_bank.setOnItemClickListener { parent, view, position, id ->
            grid_bank.visibility=View.GONE
            btn_next.visibility=View.VISIBLE
            var name=bankAdapter.getItem(position).toString()
            edit_bank.setText(name)
        }

        btn_next.setOnClickListener {
            //계좌인증 필요
            VolleyService.joinReq(email,password,loginType,nickname,userType,image,phone,edit_bank.text.toString(),edit_account.text.toString(),this,{success ->
                var intent= Intent(this,LoginActivity::class.java)

                startActivity(intent)
            })
        }
    }
}
