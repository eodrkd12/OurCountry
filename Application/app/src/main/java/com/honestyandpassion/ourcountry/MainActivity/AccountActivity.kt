package com.honestyandpassion.ourcountry.MainActivity

import android.os.AsyncTask
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_account.*
import okhttp3.*
import java.io.IOException

class AccountActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        var intent = intent
        var bankcode = intent.getIntExtra("bankcode", 0)
        var bankname = intent.getStringExtra("bankname")
        var token=""

        var editBank = findViewById<EditText>(R.id.edit_bank)
        editBank.setText(bankname)


        val items = resources.getStringArray(R.array.account_confirm)

        val myAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)

        spinner_confirm.adapter = myAdapter

       /*     val client_id = "NH1R0JVZDIFqbeMaT5jNxRUxt3MO7QgK35PfCzW6"
            var client_secret = "R6u7SWjON7KSh4VrmgVWfOanyNzmeWtwoVDs5Rym"
           /* val url =
                "https://openapi.openbanking.or.kr/oauth/2.0/token?scope=oob&client_id=" + client_id + "&client_secret=" + client_secret + "&grant_type=client_credentials"
*/
        var url="https://openapi.openbanking.or.kr/oauth/2.0/token"
            val request = Request.Builder().url(url)
                .addHeader("scope","oob")
                .addHeader("client_id",client_id)
                .addHeader("client_secret",client_secret)
                .addHeader("grant_type","client_credentials")
                .method("POST",null).build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    runOnUiThread{
                        val body = response?.body?.string()
                        println("Success to execute request : $body")

                        val gson = GsonBuilder().create()

                       // val homefeed = gson.fromJson(body, Homefeed::class.java)
                    }

                }

                override fun onFailure(call: Call, e: IOException) {
                    println("리퀘스트 실패")
                }
            })*/

    }
}