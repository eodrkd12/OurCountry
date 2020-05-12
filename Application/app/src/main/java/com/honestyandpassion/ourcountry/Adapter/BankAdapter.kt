package com.honestyandpassion.ourcountry.Adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.honestyandpassion.ourcountry.R
import android.view.LayoutInflater
import com.honestyandpassion.ourcountry.MainActivity.MainActivity
import android.widget.ImageView.ScaleType
import android.widget.GridView
import android.widget.TextView


class BankAdapter(val context: Context, val bankList: ArrayList<String>) : BaseAdapter() {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val context:Context? = parent?.context
        if (view == null) {
            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_bank, parent, false)
        }
        var textView=view?.findViewById(R.id.text_bank) as TextView
        var item=bankList[position]
        textView.setText(item)
        return view
    }

    override fun getItem(position: Int): Any {
        return bankList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return bankList.size
    }

}