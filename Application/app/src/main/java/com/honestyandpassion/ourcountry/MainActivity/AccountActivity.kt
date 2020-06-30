package com.honestyandpassion.ourcountry.MainActivity

import android.os.AsyncTask
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_account.*

class AccountActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        var intent = intent
        var bankcode = intent.getIntExtra("bankcode", 0)
        var bankname = intent.getStringExtra("bankname")
        Toast.makeText(this, bankname.toString(), Toast.LENGTH_SHORT).show()
        var editBank=findViewById<EditText>(R.id.edit_bank)
        editBank.setText(bankname)


        val items = resources.getStringArray(R.array.account_confirm)

        val myAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)

        spinner_confirm.adapter = myAdapter



    }
}