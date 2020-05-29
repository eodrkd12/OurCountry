package com.honestyandpassion.ourcountry.IntroActivity

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.StrictMode
import android.widget.Toast
import com.honestyandpassion.ourcountry.Class.UserInfo
import com.honestyandpassion.ourcountry.Fragment.HomeFragment
import com.honestyandpassion.ourcountry.MainActivity.MainActivity
import com.honestyandpassion.ourcountry.MainActivity.RegisterActivity
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //스마트폰 인터넷 사용 권한 허가
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork()
                .build())

        //알림 채널 생성
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var fcmPref=this.getSharedPreferences("FCM", Context.MODE_PRIVATE)
            if(fcmPref.getString("id","")==""){
                val notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val notificationChannel = NotificationChannel(
                    "fcm_ourcountry",
                    "fcm_ourcountry",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationChannel.description = "uniting fcm channel"
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(true)
                notificationChannel.vibrationPattern = longArrayOf(100, 200, 100, 200)
                notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
                notificationChannel.setShowBadge(true)
                notificationChannel.enableVibration(true)
                notificationManager.createNotificationChannel(notificationChannel)
            }
        }

        //프리퍼런스 검사 있으면 Main으로 startActivity 호출하고 return
        var userPref=this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        UserInfo.ID=userPref.getString("ID","")
        UserInfo.PW=userPref.getString("PW","")
        if(UserInfo.ID!="") {
            VolleyService.loginReq(UserInfo.ID, UserInfo.PW, this, { success->
                when(success.getInt("code")) {
                    0 -> {
                        //통신 실패
                        Handler().postDelayed({
                            var intent:Intent = Intent(this,LoginActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)  //액티비티 전환시 애니메이션을 무시
                            startActivity(intent)
                            finish()
                        },2000)
                        Toast.makeText(this, "서버와의 통신에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                    3 -> {
                        //로그인 성공
                        //프리퍼런스 저장
                        var user = success.getJSONObject("user")
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
                        UserInfo.USERTYPE=user.getString("user_type")


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
                            .putFloat("RATING",UserInfo.RATING)
                            .putString("JOINDATE",UserInfo.JOINDATE)
                            .putString("ACCOUNT",UserInfo.ACCOUNT)
                            .putString("ABOUT",UserInfo.ABOUT)
                            .putFloat("RATING_AVERAGE",UserInfo.RATING_AVERAGE)
                            .putInt("RATING_COUNT",UserInfo.RATING_COUNT)
                            .putString("USERTYPE", UserInfo.USERTYPE)
                            .apply()

                        Handler().postDelayed({
                            var intent:Intent = Intent(this,MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)  //액티비티 전환시 애니메이션을 무시
                            startActivity(intent)
                            finish()
                        },2000)
                    }
                }
            })
        }
        else {
            Handler().postDelayed({
                var intent= Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)  //액티비티 전환시 애니메이션을 무시
                startActivity(intent)
                finish()
            }, 2000)
        }
    }

}
