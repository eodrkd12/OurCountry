package com.honestyandpassion.ourcountry.Object

import android.content.Context
import android.graphics.Bitmap
import android.net.sip.SipSession
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Method
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

    fun addUserTypeReq(
        user_id:String,
        cur_type:String,
        change_type:String,
        date:String,
        context: Context,
        success: (JSONObject?) -> Unit
    ){
        var url="${ip}/usertypechange"

        var jsonObject=JSONObject()
        jsonObject.put("user_id",user_id)
        jsonObject.put("cur_type",cur_type)
        jsonObject.put("change_type",change_type)
        jsonObject.put("date",date)

        var request = object : JsonObjectRequest(Method.POST,
            url,
            jsonObject,
            Response.Listener {
                success(null)
            },
            Response.ErrorListener {
            }) {
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
        userType: String,
        bank:String,
        account:String,
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
        jsonObject.put("user_type", userType)
        jsonObject.put("bank",bank)
        jsonObject.put("account",account)

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

    fun editPasswordReq(
        id: String,
        password:String,
        context: Context,
        success: (JSONObject?) -> Unit
    ) {
        var url = "${ip}/user/update/password"

        var jsonObject = JSONObject()
        jsonObject.put("id", id)
        jsonObject.put("password",password)

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


    fun myInfoReq(id: String, context: Context, success: (JSONObject?) -> Unit){
        var url="${ip}/user/my"

        var jsonObject=JSONObject()
        jsonObject.put("id",id)

        var request=object : JsonObjectRequest(
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

    fun createRoomReq(maker: String, partner: String?, roomDate: String?,registerTitle: String, context: Context, success:(JSONObject?) -> Unit) {
        var url="${ip}/chat_room"

        var json=JSONObject()
        json.put("maker",maker)
        json.put("partner",partner)
        json.put("room_date",roomDate)
        json.put("room_title",registerTitle)

        var request = object : JsonObjectRequest(Method.POST,
            url,
            json,
            Response.Listener {
                Log.d("test",it.toString())
                success(it)
            },
            Response.ErrorListener {
                Log.d("test",it.toString())
            }) {
        }

        Volley.newRequestQueue(context).add(request)
    }

    fun getMyChatRoomReq(id: String, context: Context, success: (JSONArray) -> Unit) {
        var url="${ip}/chat_room/my_room"

        var json=JSONObject()
        json.put("user_id",id)

        var array=JSONArray()
        array.put(json)

        var request=object : JsonArrayRequest(Method.POST,
            url,
            array,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {
                Log.d("test",it.toString())
            }){

        }

        Volley.newRequestQueue(context).add(request)
    }

    fun getMyProductReq(userId: String,context: Context,success: (JSONArray?) -> Unit) {
        var url = "${ip}/user/my/product"

        var json = JSONObject()
        json.put("user_id", userId)

        var array = JSONArray()
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

    fun updateImageReq(id:String, bitmap: Bitmap, context:Context) {
        var url = "${ip}/user/update/image"

        var stringImage = ImageManager.BitmapToString(bitmap)

        var jsonObject = JSONObject()
        jsonObject.put("id", id)
        jsonObject.put("bitmap", stringImage)

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

    fun insertWishlistReq(registerId:Int, userId:String, context: Context, success: (String?)->Unit) {
        var url = "${ip}/wishlist/insert"

        var json = JSONObject()
        json.put("register_id", registerId)
        json.put("user_id", userId)

        var request = object : JsonObjectRequest(Method.POST,
            url,
            json,
            Response.Listener {
                success(it.getString("result"))
            },
            Response.ErrorListener {
            }) {
        }
        Volley.newRequestQueue(context).add(request)
    }

    fun deleteWishlistReq(registerId:Int, userId:String, context: Context, success: (String?)->Unit) {
        var url = "${ip}/wishlist/delete"

        var json = JSONObject()
        json.put("register_id", registerId)
        json.put("user_id", userId)

        var request = object : JsonObjectRequest(Method.POST,
            url,
            json,
            Response.Listener {
                success(it.getString("result"))
            },
            Response.ErrorListener {
            }) {
        }
        Volley.newRequestQueue(context).add(request)
    }


    fun checkWishlistReq(registerId:Int, userId:String, context: Context, success: (JSONObject?)->Unit) {
        var url = "${ip}/wishlist/check"

        var json = JSONObject()
        json.put("register_id", registerId)
        json.put("user_id", userId)

        var request = object : JsonObjectRequest(Method.POST,
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

    fun insertTokenReq(id: String, token: String?, context: Context) {
        var url = "${ip}/user/insert/token"

        var jsonObject = JSONObject()
        jsonObject.put("id", id)
        jsonObject.put("token",token)

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

    fun removeToken(id: String, context: Context) {
        var url = "${ip}/user/remove/token"

        var jsonObject = JSONObject()
        jsonObject.put("id", id)

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

    fun getSubCategoryProductReq(subCategory: String, context: Context, success: (JSONArray) -> Unit) {
        var url="${ip}/register/subcategory"

        var json=JSONObject()
        json.put("product_subcategory", subCategory)

        var array = JSONArray()
        array.put(json)

        var request=object : JsonArrayRequest(Method.POST,
            url,
            array,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {

            }){

        }

        Volley.newRequestQueue(context).add(request)
    }

    fun getWishlistProductReq(userId: String, context: Context, success: (JSONArray) -> Unit) {
        var url="${ip}/wishlist/get"

        var json=JSONObject()
        json.put("user_id", userId)

        var array = JSONArray()
        array.put(json)

        var request=object : JsonArrayRequest(Method.POST,
            url,
            array,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {

            }){
        }
        Volley.newRequestQueue(context).add(request)
    }

    fun paymentReq(
        orderId: String,
        registerId: Int?,
        userId: String,
        registerPrice: String,
        paymentDate: String?,
        type: String,
        registerTitle: String?,
        seller: String,
        context: Context
    ) {
        var url="${ip}/payment"

        var json=JSONObject()
        json.put("order_id",orderId)
            .put("register_id",registerId)
            .put("user_id",userId)
            .put("register_price",registerPrice)
            .put("payment_date",paymentDate)
            .put("type",type)
            .put("register_title",registerTitle)
            .put("seller",seller)

        var request=object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
            },
            Response.ErrorListener {
            }) {
        }
        Volley.newRequestQueue(context).add(request)
    }

    fun updateProductReq(registerId: Int,userId:String, registerTitle:String, productCategory:String, productSubCategory:String, productType:String, productStatus:String, productBrand:String, productPrice:String, sellerStore:Int, registerContent:String, tradeOption:String, sellerAddress: String, registerDate: String, context: Context, success:(String?) -> Unit) {
        var url = "${ip}/register/update"

        var jsonObject = JSONObject()
        jsonObject.put("register_id",registerId)
            .put("user_id", userId)
            .put("register_title", registerTitle)
            .put("product_category", productCategory)
            .put("product_subcategory", productSubCategory)
            .put("product_type", productType)
            .put("product_status", productStatus)
            .put("product_brand", productBrand)
            .put("product_price", productPrice)
            .put("seller_store", sellerStore)
            .put("register_content", registerContent)
            .put("trade_option", tradeOption)
            .put("seller_address", sellerAddress)
            .put("register_date", registerDate)

        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            jsonObject,
            Response.Listener {
                success(it.getString("result"))
            },
            Response.ErrorListener {
            }) {
        }
        Volley.newRequestQueue(context).add(request)
    }

    fun increaseViewReq(registerId: Int, context: Context, success: (String?) -> Unit) {
        var url="${ip}/register/increase/view"

        var json=JSONObject()
        json.put("register_id", registerId)

        var array = JSONArray()
        array.put(json)

        var request=object : JsonObjectRequest(Method.POST,
            url,
            json,
            Response.Listener {
                success(it.getString("result"))
            },
            Response.ErrorListener {

            }){
        }
        Volley.newRequestQueue(context).add(request)
    }


    fun checkViewReq(userId:String, registerId:Int, context: Context, success: (JSONObject?)->Unit) {
        var url = "${ip}/view/check"

        var json = JSONObject()
        json.put("register_id", registerId)
        json.put("user_id", userId)

        var request = object : JsonObjectRequest(Method.POST,
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

    fun insertViewReq(userId:String, registerId:Int, viewDate:String, registerTitle: String, context: Context, success: (JSONObject?)->Unit) {
        var url = "${ip}/view/insert"

        var json = JSONObject()
        json.put("register_id", registerId)
        json.put("user_id", userId)
        json.put("view_date", viewDate)
        json.put("register_title", registerTitle)

        var request = object : JsonObjectRequest(Method.POST,
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

    fun updateViewReq(userId:String, registerId:Int, viewDate:String, context: Context, success: (JSONObject?)->Unit) {
        var url = "${ip}/view/update"

        var json = JSONObject()
        json.put("register_id", registerId)
        json.put("user_id", userId)
        json.put("view_date", viewDate)

        var request = object : JsonObjectRequest(Method.POST,
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

    fun getViewReq(userId:String, context: Context, success: (JSONArray?)->Unit) {
        var url = "${ip}/view/get"

        var json = JSONObject()
        json.put("user_id", userId)

        var array = JSONArray()
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

    fun getRegisterReq(registerId: Int, context: Context, success: (JSONObject?)->Unit) {
        var url="${ip}/register/get/byid"

        var json=JSONObject()
        json.put("register_id",registerId)

        var request = object : JsonObjectRequest(Method.POST,
        url,
        json,
        Response.Listener {
            success(it)
        },
        Response.ErrorListener {
            Log.d("test",it.toString())
        }) {

        }
        Volley.newRequestQueue(context).add(request)
    }

    fun checkFollowReq(follower:String, following:String, context: Context, success: (JSONObject?)->Unit) {
        var url = "${ip}/follow/check"

        var json = JSONObject()
        json.put("follower", follower)
        json.put("following", following)

        var request = object : JsonObjectRequest(Method.POST,
            url,
            json,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {
                Log.d("test",it.toString())
            }){
            }
        Volley.newRequestQueue(context).add(request)
    }

    fun insertFollowReq(follower:String, following:String, context: Context, success: (String?)->Unit) {
        var url = "${ip}/follow/insert"

        var json = JSONObject()
        json.put("follower", follower)
        json.put("following", following)

        var request = object : JsonObjectRequest(Method.POST,
            url,
            json,
            Response.Listener {
                success(it.getString("result"))
            },
            Response.ErrorListener {
            }) {
        }
        Volley.newRequestQueue(context).add(request)
    }

    fun deleteFollowReq(follower:String, following:String, context: Context, success: (String?)->Unit) {
        var url = "${ip}/follow/delete"

        var json = JSONObject()
        json.put("follower", follower)
        json.put("following", following)

        var request = object : JsonObjectRequest(Method.POST,
            url,
            json,
            Response.Listener {
                success(it.getString("result"))
            },
            Response.ErrorListener {
            }) {
        }
        Volley.newRequestQueue(context).add(request)
    }

    fun getFollowingProductReq(follower: String, context: Context, success: (JSONArray) -> Unit) {
        var url="${ip}/follow/get"

        var json=JSONObject()
        json.put("follower", follower)

        var array = JSONArray()
        array.put(json)

        var request=object : JsonArrayRequest(Method.POST,
            url,
            array,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {

            }){
        }
        Volley.newRequestQueue(context).add(request)
    }

    fun checkChatRoomReq(maker: String, partner: String?, registerTitle: String?, context: Context, success: (Int)->Unit) {
        var url="${ip}/chat_room/check"

        var json=JSONObject()

        json.put("maker",maker)
            .put("partner",partner)
            .put("register_title",registerTitle)

        var request=object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                Log.d("test",it.toString())
                success(1)
            },
            Response.ErrorListener {
                Log.d("test",it.toString())
                success(0)
            }){

        }

        Volley.newRequestQueue(context).add(request)
    }
    fun getCountFollowerReq(following:String , context: Context, success: (JSONObject?)->Unit) {
        var url = "${ip}/follow/get/count/follower"

        var json = JSONObject()
        json.put("following", following)

        var request = object : JsonObjectRequest(Method.POST,
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

    fun getCountFollowingReq(follower:String , context: Context, success: (JSONObject?)->Unit) {
        var url = "${ip}/follow/get/count/following"

        var json = JSONObject()
        json.put("follower", follower)

        var request = object : JsonObjectRequest(Method.POST,
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

    fun getRoomInfoReq(maker: String, partner: String?, registerTitle: String, context: Context, success: (JSONObject?)->Unit) {
        var url="${ip}/chat_room/get_room_info"

        var json=JSONObject()
        json.put("maker",maker)
        json.put("partner",partner)
        json.put("room_title",registerTitle)

        var request=object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                Log.d("test",it.toString())
                success(it)
            },
            Response.ErrorListener {
                Log.d("test",it.toString())
            }
        ){}

        Volley.newRequestQueue(context).add(request)
    }

    fun getPoint(id: String, context: Context, success: (JSONObject) -> Unit) {
        var url="${ip}/user/get/point"

        var json=JSONObject().put("id",id)

        var request=object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {
            }
        ){}

        Volley.newRequestQueue(context).add(request)

    }

    fun refundReq(
        id: String,
        refundDate: String?,
        point: Int,
        bank: String,
        account: String,
        context: Context,
        success: (JSONObject) -> Unit
    ) {
        var url="${ip}/point/insert/refund"

        var json=JSONObject()
            .put("id",id)
            .put("refund_date",refundDate)
            .put("point",point)
            .put("bank",bank)
            .put("account",account)

        var request=object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {
            }
        ){}

        Volley.newRequestQueue(context).add(request)
    }

    fun deleteAccountReq(id: String, context: Context, success: (String) -> Unit) {
        var url="${ip}/user/delete"

        var json=JSONObject()
            .put("id",id)

        var request=object : JsonObjectRequest(
            Method.POST,
            url,
            json,
            Response.Listener {
                success(it.getString("result"))
            },
            Response.ErrorListener {

            }
        ){}

        Volley.newRequestQueue(context).add(request)
    }

}