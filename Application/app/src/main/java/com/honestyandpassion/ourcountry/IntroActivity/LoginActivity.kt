package com.honestyandpassion.ourcountry.IntroActivity

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
