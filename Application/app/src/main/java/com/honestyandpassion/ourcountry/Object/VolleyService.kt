package com.honestyandpassion.ourcountry.Object

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.time.LocalDateTime
import java.util.*

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

    fun registerProductReq(userId: String, registerTitle:String, productCategory:String, productSubCategory:String, productType:String, productStatus:String, productBrand:String, productPrice:String, sellerStore:Int, registerContent:String, tradeOption:String, sellerAddress:String, registerDate: Date, registerLike:Int, registerView:Int, context: Context, success: (JSONObject?) -> Unit){
        var url="${ip}/register"


        var json=JSONObject()
        json.put("user_id", userId)
        json.put("register_title", registerTitle)
        json.put("product_category", productCategory)
        json.put("product_subcategory", productSubCategory)
        json.put("product_type", productType)
        json.put("product_status", productStatus)
        json.put("product_brand", productBrand)
        json.put("product_price", productPrice)
        json.put("seller_store", sellerStore)
        json.put("register_content", registerContent)
        json.put("trade_option", tradeOption)
        json.put("seller_address", sellerAddress)
        json.put("register_date", registerDate)
        json.put("register_like", registerLike)
        json.put("register_view", registerView)


        var request=object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {
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
    fun testReq(context: Context, success: (JSONObject) -> Unit){
        var url = "${ip}/register"

        var json = JSONObject()
        var request=object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {
            }){

        }

        Volley.newRequestQueue(context).add(request)
    }


}