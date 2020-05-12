package com.honestyandpassion.ourcountry.Class

import android.content.Context
import com.kakao.auth.*
import com.kakao.auth.AuthType



class KakaoSDKAdapter : KakaoAdapter() {

    public override fun getSessionConfig(): ISessionConfig {
        return object : ISessionConfig {
            override fun isSaveFormData(): Boolean {
                return true
            }

            override fun getAuthTypes(): Array<AuthType> {
                return arrayOf(AuthType.KAKAO_LOGIN_ALL)
            }

            override fun isSecureMode(): Boolean {
                return true
            }

            override fun getApprovalType(): ApprovalType? {
                return ApprovalType.INDIVIDUAL
            }

            override fun isUsingWebviewTimer(): Boolean {
                return true
            }

        }
    }

    override fun getApplicationConfig(): IApplicationConfig {
        return object : IApplicationConfig{
            override fun getApplicationContext(): Context {
                return OurCountry.getGlobalApplicationContext()
            }

        }
    }

}