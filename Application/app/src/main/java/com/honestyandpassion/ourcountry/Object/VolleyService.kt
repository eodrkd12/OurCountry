package com.honestyandpassion.ourcountry.Object

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object VolleyService {
    val ip = "http://107.180.93.143:3000"

    fun emailCheckReq(email: String, context: Context, success: (String?) -> Unit) {
        var url = "${ip}/user/check/email"

        var json = JSONObject()
        json.put("email", email)

        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                Log.d("test", "${it}")
                success("fail")
            },
            Response.ErrorListener {
                Log.d("test", "${it}")
                if (it is com.android.volley.ParseError) success("success")
            }) {

        }

        Volley.newRequestQueue(context).add(request)
    }

    fun registerProductReq(
        userId: String,
        registerTitle: String,
        productCategory: String,
        productSubCategory: String,
        productType: String,
        productStatus: String,
        productBrand: String,
        productPrice: String,
        sellerStore: Int,
        registerContent: String,
        tradeOption: String,
        sellerAddress: String,
        registerDate: String,
        registerLike: Int,
        registerView: Int,
        userNickname: String,
        context: Context,
        success: (JSONObject?) -> Unit
    ) {
        var url = "${ip}/register/insert"

        var json = JSONObject()
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
        json.put("user_nickname",userNickname)

        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {

            }) {

        }
        Volley.newRequestQueue(context).add(request)
    }


    fun testReq(context: Context, success: (JSONObject?) -> Unit) {
        var url = "${ip}/user/login"

        var json = JSONObject()

        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {
            }) {

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
                Log.d("test",it.toString())
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
        }
        //요청을 보내는 부분
        Volley.newRequestQueue(context).add(request)
    }

    fun joinReq(
        id: String,
        password: String,
        loginType: String,
        nickname: String,
        userType: String,
        image: String,
        phone: String,
        userBank: String,
        userAccount: String,
        context: Context,
        success: (JSONObject) -> Unit
    ) {
        val url = "${ip}/user/join"

        val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
        val joinDate = current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val json = JSONObject()
        json.put("id", id)
        json.put("password", password)
        json.put("nickname", nickname)
        json.put("user_type", userType)
        json.put("user_bank", userBank)
        json.put("user_account", userAccount)
        json.put("user_join_date", joinDate)
        json.put("image",image)
        json.put("phone",phone)
        json.put("login_type",loginType)

        var request = object : JsonObjectRequest(Method.POST
            , url
            , json
            , Response.Listener {
                success(it)
            }
            , Response.ErrorListener {

            }
        ) {
        }
        Volley.newRequestQueue(context).add(request)
    }

    //요청을 보내는 부분
    fun editUserReq(
        id: String,
        nickname: String,
        phone: String,
        about: String,
        address: String,
        context: Context,
        success: (JSONObject?) -> Unit
    ) {
        var url = "${ip}/user/update/editUserReq"

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
                success(it)
            },
            Response.ErrorListener {

            }) {
        }
        Volley.newRequestQueue(context).add(request)
    }

    fun myInfoReq(id: String, context: Context, success: (String?) -> Unit){
        var url="${ip}/user/my"

        var jsonObject=JSONObject()
        jsonObject.put("id",id)

        var request=object : JsonObjectRequest(
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

    fun sendFCMReq(roomId: String, title: String, content: String, context: Context) {

        var url = "${ip}/chat_room/fcm/send"

        var json = JSONObject()
        json.put("topic", roomId)
        json.put("content", content)
        json.put("title",title)

        var request = object : JsonObjectRequest(Method.POST,
            url,
            json,
            Response.Listener {
            },
            Response.ErrorListener {
            }) {
        }

        Volley.newRequestQueue(context).add(request)
    }

    fun insertImageReq(registerId: Int, registerTitle: String, image: Bitmap, context: Context,success:(Unit?)->Unit){
        var url = "${ip}/register/insert/image"

        var stringImage = ImageManager.BitmapToString(image)

        var json = JSONObject()
        json.put("register_id", registerId)
        json.put("register_title", registerTitle)
        json.put("image", stringImage)

        var request = object : JsonObjectRequest(Method.POST,
            url,
            json,
            Response.Listener {
                success(null)
            },
            Response.ErrorListener {
            }) {
        }

        Volley.newRequestQueue(context).add(request)
    }

    fun recentRegisterReq(context: Context, success: (JSONArray?) -> Unit){
        var url = "${ip}/register/recent"

        var array = JSONArray()

        var request = object : JsonArrayRequest(Method.POST,
            url,
            array,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {
            }) {
        }

        Volley.newRequestQueue(context).add(request)
    }
    fun popularRegisterReq(context: Context, success: (JSONArray?) -> Unit){
        var url = "${ip}/register/popular"

        var array = JSONArray()

        var request = object : JsonArrayRequest(Method.POST,
            url,
            array,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {
            }) {
        }

        Volley.newRequestQueue(context).add(request)
    }

    fun getProductImageReq(registerId: Int, context: Context, success: (JSONArray?) -> Unit) {
        var url = "${ip}/register/image"

        var json = JSONObject()
        json.put("register_id", registerId)

        var array=JSONArray()
        array.put(json)

        var request = object : JsonArrayRequest(Method.POST,
            url,
            array,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {

            }) {
        }
        Volley.newRequestQueue(context).add(request)
    }

    fun searchRegisterReq(searchText:String, context: Context, success: (JSONArray?) -> Unit){
        var url = "${ip}/register/search"

        var json = JSONObject()
        json.put("searchText", searchText)

        var array = JSONArray()

        var request = object : JsonArrayRequest(Method.POST,
            url,
            array,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {
            }) {
        }

        Volley.newRequestQueue(context).add(request)
    }
}