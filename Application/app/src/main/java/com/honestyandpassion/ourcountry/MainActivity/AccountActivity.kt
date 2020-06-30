package com.honestyandpassion.ourcountry.MainActivity

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_account.*

class AccountActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        val items = resources.getStringArray(R.array.account_confirm)

        val myAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)

        spinner_confirm.adapter = myAdapter

    }
}