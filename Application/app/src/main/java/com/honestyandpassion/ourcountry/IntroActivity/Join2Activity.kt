package com.honestyandpassion.ourcountry.IntroActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.honestyandpassion.ourcountry.Class.GMailSender
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_join2.*

class Join2Activity : AppCompatActivity() {

    companion object{
        var code:String=""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join2)

        var email=intent.getStringExtra("email")
        var nickname=intent.getStringExtra("nickname")
        var password=intent.getStringExtra("password")

        var mailTask=MailTask(email)
        mailTask.execute()

        btn_next.setOnClickListener {
            if(code==edit_code.text.toString()){
                var intent= Intent(this,Join3Activity::class.java)
                intent.putExtra("email",email)
                intent.putExtra("nickname",nickname)
                intent.putExtra("password",password)
                startActivity(intent)
                text_mismatch.visibility=View.GONE
            }
            else{
                edit_code.setText("")
                text_mismatch.text="코드가 일치하지 않습니다."
                text_mismatch.visibility= View.VISIBLE
            }
        }

        btn_resend.setOnClickListener {
            code=""
            var reMailTask=MailTask(email)
            reMailTask.execute()
            text_mismatch.text="코드를 재전송하였습니다."
            text_mismatch.visibility=View.VISIBLE
        }

        text_back.setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }



    class MailTask(var email: String) : AsyncTask<Unit,Unit,String>(){
        override fun doInBackground(vararg params: Unit?): String {
            sendCode(email)

            return "SEND CODE"
        }

        fun sendCode(email:String){

            for(i in 0..5){
                code += (0..9).random().toString()
            }
            var mailSender= GMailSender("eodrkd12@gmail.com","dnflwlq12!",code)
            mailSender.sendMail(
                "우리 시골 이메일 인증"
                , "안녕하세요.\n" +
                        "아래 인증 코드를 애플리케이션에서 입력하여 회원가입을 진행해주십시오.\n" +
                        "인증코드 : [${code}]\n" +
                        "감사합니다."
                , email
            )
            Log.d("test",code)
            cancel(true)
        }

        override fun onCancelled() {
            super.onCancelled()
            Log.d("test","AsyncTest 취소")
        }
    }
}
