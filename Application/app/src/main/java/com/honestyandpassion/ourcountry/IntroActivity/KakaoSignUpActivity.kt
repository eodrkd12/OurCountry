package com.honestyandpassion.ourcountry.IntroActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.honestyandpassion.ourcountry.R

import com.kakao.auth.ApiResponseCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.usermgmt.response.model.UserAccount

import com.kakao.util.helper.log.Logger;
import android.content.Intent
import com.honestyandpassion.ourcountry.MainActivity.MainActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class KakaoSignUpActivity : AppCompatActivity() {

    val TAG="OurCountry"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestMe()
    }

    fun requestMe(){
        UserManagement.getInstance().me(object : MeV2ResponseCallback(){
            override fun onSuccess(result: MeV2Response?) {
                var id=result!!.id
                var account=result.kakaoAccount
                Log.d(TAG,"${id} // ${account}")
                var email=account.email
                var phone=account.phoneNumber
                var image=account.profile.profileImageUrl
                var nickname=account.profile.nickname


            }

            override fun onSessionClosed(errorResult: ErrorResult?) {
                redirectLoginActivity()
            }

        })
    }

    protected fun redirectLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        startActivity(intent)
        finish()
    }
}
