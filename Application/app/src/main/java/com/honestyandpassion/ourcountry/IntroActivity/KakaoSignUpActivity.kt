package com.honestyandpassion.ourcountry.IntroActivity

import android.content.Context
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
import android.widget.Toast
import com.google.firebase.iid.FirebaseInstanceId
import com.honestyandpassion.ourcountry.Class.UserInfo
import com.honestyandpassion.ourcountry.Object.VolleyService


class KakaoSignUpActivity : AppCompatActivity() {

    val TAG="OurCountry"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestMe()
    }

    fun requestMe(){
        UserManagement.getInstance().me(object : MeV2ResponseCallback(){
            override fun onSuccess(result: MeV2Response?) {
                var id=result!!.id.toString()
                var account=result.kakaoAccount
                Log.d(TAG,"${id} // ${account}")
                //var email=account.email
                //var phone=account.phoneNumber
                var image=account.profile.profileImageUrl
                var nickname=account.profile.nickname

                VolleyService.emailCheckReq(id,this@KakaoSignUpActivity,{success ->
                    when(success){
                        "success" -> {
                            //회원가입
                            var intent=Intent(this@KakaoSignUpActivity,Join3Activity::class.java)
                            intent.putExtra("email",id)
                            intent.putExtra("nickname",nickname)
                            intent.putExtra("phone","")
                            intent.putExtra("image",image)
                            intent.putExtra("password","")
                            intent.putExtra("login_type","kakao")
                            startActivity(intent)
                        }
                        "fail" -> {
                            //로그인
                            VolleyService.loginReq(id,"",this@KakaoSignUpActivity, {success ->
                                when(success.getInt("code")){
                                    0 -> {
                                        Toast.makeText(this@KakaoSignUpActivity,"서버와의 통신에 실패했습니다.",Toast.LENGTH_SHORT).show()
                                    }
                                    1 -> {
                                        Toast.makeText(this@KakaoSignUpActivity,"계정을 확인해주세요.",Toast.LENGTH_SHORT).show()
                                    }
                                    2 -> {
                                        Toast.makeText(this@KakaoSignUpActivity,"ID / PW를 확인해주세요.",Toast.LENGTH_SHORT).show()
                                    }
                                    3 -> {
                                        var token= FirebaseInstanceId.getInstance().token
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
                                        UserInfo.RATING_AVERAGE=user.getDouble("user_rating_average").toFloat()
                                        UserInfo.RATING_COUNT=user.getInt("user_rating_count")
                                        UserInfo.TOKEN=token!!

                                        VolleyService.insertTokenReq(UserInfo.ID,token,this@KakaoSignUpActivity)

                                        var pref=this@KakaoSignUpActivity.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                                        var editor=pref.edit()
                                        editor.putString("ID", UserInfo.ID)
                                            .putString("PW", UserInfo.PW)
                                            .putString("LOGIN_TYPE", UserInfo.LOGIN_TYPE)
                                            .putString("NICKNAME", UserInfo.NICKNAME)
                                            .putString("TYPE", UserInfo.TYPE)
                                            .putString("PHONE", UserInfo.PHONE)
                                            .putString("ADDRESS", UserInfo.ADDRESS)
                                            .putString("IMAGE", UserInfo.IMAGE)
                                            .putInt("POINT", UserInfo.POINT)
                                            .putFloat("RATING", UserInfo.RATING as Float)
                                            .putString("JOINDATE", UserInfo.JOINDATE)
                                            .putString("ACCOUNT", UserInfo.ACCOUNT)
                                            .putString("ABOUT", UserInfo.ABOUT)
                                            .putFloat("RATING_AVERAGE",UserInfo.RATING_AVERAGE)
                                            .putInt("RATING_COUNT",UserInfo.RATING_COUNT)
                                            .putString("TOKEN",UserInfo.TOKEN)
                                            .apply()

                                        var intent:Intent=Intent(this@KakaoSignUpActivity,MainActivity::class.java)
                                        startActivity(intent)
                                    }
                                }
                            })
                        }
                    }
                })
            }

            override fun onSessionClosed(errorResult: ErrorResult?) {
                Toast.makeText(this@KakaoSignUpActivity,"${errorResult}",Toast.LENGTH_LONG).show()
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
