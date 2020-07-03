package com.honestyandpassion.ourcountry.MainActivity

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_account.*
import okhttp3.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AccountActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        var intent = intent
        var bankcode = intent.getIntExtra("bankcode", 0).toString()
        var bankname = intent.getStringExtra("bankname")
        var accessToken=""

        var editBank = findViewById<EditText>(R.id.edit_bank)
        editBank.setText(bankname)


        val items = resources.getStringArray(R.array.account_confirm)

        val myAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)

        spinner_confirm.adapter = myAdapter

           val client_id = "6HTVQ1TFPQ09ONDVX4DmsXRsmin0U3Ue22xvWu6A"
           var client_secret = "j5vtfG463M3BXfndJtyz5XFHnFGDQeTVLReZ500w"


        var url="https://testapi.openbanking.or.kr/oauth/2.0/token"

        val requestBody : RequestBody = FormBody.Builder()
            .add("scope","oob")
            .add("client_id",client_id)
            .add("client_secret",client_secret)
            .add("grant_type","client_credentials")
            .build()
            val request = Request.Builder().url(url)
                .post(requestBody)
                .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val body = response?.body?.string()
                    var strArray=body!!.split(":")
                    accessToken=strArray[1].replace("\"","")
                    Log.d("test","${accessToken}")

                    runOnUiThread{

                        val gson = GsonBuilder().create()

                       // val homefeed = gson.fromJson(body, Homefeed::class.java)
                    }

                }

                override fun onFailure(call: Call, e: IOException) {
                    Log.d("test","실패 ")
                }
            })

        btn_confirm.setOnClickListener {

            var clientNum="2020163807"
            var uniqueNum= SimpleDateFormat("HHmmssSSS", Locale.KOREA).format(Date())
            var confirmText=spinner_confirm.selectedItem.toString()

            var accountNum=text_account.text.toString() //계좌번호
            var confirmNum = "" //인증확인번호
            var confirm=text_confirm_number.text.toString()//인증번호
            var BankTranId=clientNum+"U"+uniqueNum //은행거래고유번호
            var time = SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(Date()) //시간

            if(confirmText=="주민등록번호"){
                confirmNum="1"
            }else if(confirmText=="사업자등록번호"){
                confirmNum="6"
            }

            var url="https://testapi.openbanking.or.kr/v2.0/inquiry/real_name"

            val requestBody : RequestBody = FormBody.Builder()
                .add("bank_tran_id",BankTranId)
                .add("bank_code_std",bankcode)
                .add("account_num",accountNum)
                .add("account_holder_info_type",confirmNum)
                .add("account_holder_info",confirm)
                .add("tran_dtime",time)
                .build()
            val request = Request.Builder().url(url)
                .addHeader("Authorization","Bearer<"+accessToken+">")
                .post(requestBody)
                .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val body = response?.body?.string()
                 Log.d("test","${body}")
                    runOnUiThread{

                        val gson = GsonBuilder().create()

                        // val homefeed = gson.fromJson(body, Homefeed::class.java)
                    }

                }

                override fun onFailure(call: Call, e: IOException) {
                    Log.d("test","실패 ")
                }
            })




        }

    }

}