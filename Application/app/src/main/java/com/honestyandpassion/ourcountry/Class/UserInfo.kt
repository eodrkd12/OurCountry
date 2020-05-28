package com.honestyandpassion.ourcountry.Class

import android.app.Application
import java.util.*

class UserInfo : Application() {
    companion object{
        var ID : String=""
        var PW : String=""
        var LOGIN_TYPE : String=""
        var NICKNAME : String=""
        var TYPE : String=""
        var PHONE : String=""
        var ADDRESS : String=""
        var IMAGE : String=""
        var POINT : Int=0
        var RATING : Float=0.0f
        var JOINDATE : String=""
        var ACCOUNT : String=""
        var ABOUT: String =""
        var BANK:String=""
        var RATING_AVERAGE:Float=0.0f
        var RATING_COUNT:Int=0
        var TOKEN:String=""
        var USERTYPE:String=""
    }
}