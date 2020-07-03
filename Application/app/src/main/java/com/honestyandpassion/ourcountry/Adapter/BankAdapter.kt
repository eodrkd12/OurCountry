package com.honestyandpassion.ourcountry.Adapter

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.honestyandpassion.ourcountry.R
import android.view.LayoutInflater
import com.honestyandpassion.ourcountry.MainActivity.MainActivity
import android.widget.ImageView.ScaleType
import android.widget.GridView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.honestyandpassion.ourcountry.MainActivity.AccountActivity
import com.honestyandpassion.ourcountry.MainActivity.ChangePasswordActivity


class BankAdapter(val context: Context, val bankList: Array<String>) : BaseAdapter() {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val context:Context? = parent?.context
        if (view == null) {
            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_bank, parent, false)
        }
        var textView=view?.findViewById(R.id.text_bank) as TextView
        var item=bankList[position].split(" ")
        textView.setText(item[0])


        return view
    }

    override fun getItem(position: Int): Any {
        var item=bankList[position].split(" ")
        var intent = Intent(context, AccountActivity::class.java)
        intent.putExtra("bankcode",item[1])
        intent.putExtra("bankname",item[0])


        context.startActivity(intent)

        return item[0]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return bankList.size
    }

}