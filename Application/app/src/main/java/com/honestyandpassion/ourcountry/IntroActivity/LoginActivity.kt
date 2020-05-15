package com.honestyandpassion.ourcountry.IntroActivity

import android.content.Context
import android.content.pm.PackageInstaller
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.KakaoSDK;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.content.Intent
import android.widget.Toast
import com.honestyandpassion.ourcountry.Class.UserInfo
import com.honestyandpassion.ourcountry.MainActivity.MainActivity
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.R
import com.kakao.auth.AuthType
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    var sessionCallback:ISessionCallback?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_kakao_login.setOnClickListener {
            sessionCallback=SessionCallback()
            Session.getCurrentSession().addCallback(sessionCallback)
            Session.getCurrentSession().checkAndImplicitOpen()
            Session.getCurrentSession().open(AuthType.KAKAO_LOGIN_ALL,this)
        }

        btn_join.setOnClickListener {
            var intent=Intent(this,Join1Activity::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener {
            var id=edit_id.text.toString()
            var pw=edit_password.text.toString()
            VolleyService.loginReq(id,pw,this, {success ->
                when(success.getInt("code")){
                    0 -> {
                        Toast.makeText(this,"서버와의 통신에 실패했습니다.",Toast.LENGTH_SHORT).show()
                    }
                    1 -> {
                        Toast.makeText(this,"계정을 확인해주세요.",Toast.LENGTH_SHORT).show()
                    }
                    2 -> {
                        Toast.makeText(this,"ID / PW를 확인해주세요.",Toast.LENGTH_SHORT).show()
                    }
                    3 -> {
                        var user=success.getJSONObject("user")
                        UserInfo.ID=user.getString("user_id")
                        UserInfo.PW=user.getString("user_pw")
                        UserInfo.LOGIN_TYPE=user.getString("user_login_type")
                        UserInfo.NICKNAME=user.getString("user_nickname")
                        UserInfo.TYPE=user.getString("user_type")
                        UserInfo.PHONE=user.getString("user_phone")
                        UserInfo.ADDRESS=user.getString("user_address")
                        UserInfo.IMAGE=user.getString("user_image")
                        UserInfo.POINT=user.getInt("user_point")
                        UserInfo.RATING=user.getDouble("user_rating").toFloat()
                        UserInfo.JOINDATE=user.getString("user_join_date")
                        UserInfo.ACCOUNT=user.getString("user_account")
                        UserInfo.ABOUT=user.getString("user_about")

                      //  VolleyService.insertTokenReq(UserInfo.NICKNAME,token,this)

                        var pref=this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                        var editor=pref.edit()
                        editor.putString("ID",UserInfo.ID)
                            .putString("PW",UserInfo.PW)
                            .putString("LOGIN_TYPE",UserInfo.LOGIN_TYPE)
                            .putString("NICKNAME",UserInfo.NICKNAME)
                            .putString("TYPE",UserInfo.TYPE)
                            .putString("PHONE",UserInfo.PHONE)
                            .putString("ADDRESS",UserInfo.ADDRESS)
                            .putString("IMAGE",UserInfo.IMAGE)
                            .putInt("POINT",UserInfo.POINT)
                            .putFloat("RATING",UserInfo.RATING as Float)
                            .putString("JOINDATE",UserInfo.JOINDATE)
                            .putString("ACCOUNT",UserInfo.ACCOUNT)
                            .putString("ABOUT",UserInfo.ABOUT)
                            .apply()

                        var intent:Intent=Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    }
                }

            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(Session.getCurrentSession().handleActivityResult(requestCode,resultCode,data)){
            return
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        Session.getCurrentSession().removeCallback(sessionCallback)
    }

    private inner class SessionCallback : ISessionCallback {

        override fun onSessionOpened() {
            redirectSignupActivity()  // 세션 연결성공 시 redirectSignupActivity() 호출
        }

        override fun onSessionOpenFailed(exception: KakaoException?) {
            if (exception != null) {
                Logger.e(exception)
            }
            setContentView(com.honestyandpassion.ourcountry.R.layout.activity_login) // 세션 연결이 실패했을때
        }
    }

    fun redirectSignupActivity(){
        var intent= Intent(this,KakaoSignUpActivity::class.java)
        intent.flags=Intent.FLAG_ACTIVITY_NO_ANIMATION
        startActivity(intent)
        finish()
    }
}
