package com.honestyandpassion.ourcountry.Object

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

object VolleyService {
    val ip="http://107.180.93.143:3000"

    fun emailCheckReq(email:String,context: Context,success: (String?) -> Unit){
        var url="${ip}/user/check/email"

        var json=JSONObject()
        json.put("email",email)

        var request=object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                Log.d("test","${it}")
                success("fail")
            },
            Response.ErrorListener {
                Log.d("test","${it}")
                if(it is com.android.volley.ParseError) success("success")
            }){

        }

        Volley.newRequestQueue(context).add(request)
    }
    fun loginReq(id: String, pw: String, context: Context, success: (JSONObject) -> Unit) {
        val url = "${ip}/user/login"

        val json = JSONObject()
        json.put("id", id)

        var result = JSONObject()

        var request = object : JsonObjectRequest(Method.POST
            , url
            , json
            , Response.Listener {
                result.put("user", it)
                if (pw != it.getString("user_pw"))
                    result.put("code", 2)
                else if (pw == it.getString("user_pw"))
                    result.put("code", 3)
                success(result)
            }
            , Response.ErrorListener {
                if (it is com.android.volley.TimeoutError) {
                    Log.d("test", "TimeoutError")
                    result.put("code", 0)
                } else if (it is com.android.volley.ParseError) {
                    Log.d("test", "ParserError")
                    result.put("code", 1)
                }
                success(result)
            }
        ) {
            override fun getBodyContentType(): String {
                return "application/json"
            }
        }
        //요청을 보내는 부분
        Volley.newRequestQueue(context).add(request)
    }
    fun edit_user(id:String,nickname:String,phone:String,about:String,address:String, context:Context, success: (JSONObject?) -> Unit) {
        var url = "${ip}/user/update/edit_user"

        var jsonObject = JSONObject()
        jsonObject.put("id", id)
        jsonObject.put("nickname", nickname)
        jsonObject.put("phone", phone)
        jsonObject.put("about", about)
        jsonObject.put("address", address)

        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            jsonObject,
            Response.Listener {
            },
            Response.ErrorListener {

            }) {
        }
        Volley.newRequestQueue(context).add(request)
    }


}