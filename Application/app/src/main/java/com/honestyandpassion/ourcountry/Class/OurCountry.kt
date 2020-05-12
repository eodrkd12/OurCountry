package com.honestyandpassion.ourcountry.Class

import android.app.Activity
import android.app.Application
import com.kakao.auth.KakaoSDK

class OurCountry : Application() {
    companion object {
        var instance:OurCountry?=null
        var currentActivity:Activity?=null

        fun getGlobalApplicationContext() : OurCountry{
            if(instance==null)
                throw IllegalStateException("this application does not inherit com.kakao.OurCountry");
            return instance!!
        }
    }

    override fun onCreate() {
        super.onCreate()

        instance=this
        KakaoSDK.init(KakaoSDKAdapter())
    }

    override fun onTerminate() {
        super.onTerminate()
        instance=null
    }
}