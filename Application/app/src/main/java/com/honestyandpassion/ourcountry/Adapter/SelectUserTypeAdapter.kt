package com.honestyandpassion.ourcountry.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.honestyandpassion.ourcountry.IntroActivity.SelectCategoryActivity
import com.honestyandpassion.ourcountry.IntroActivity.SelectSubCategoryActivity
import com.honestyandpassion.ourcountry.IntroActivity.SelectUserTypeActivity
import com.honestyandpassion.ourcountry.Item.Category
import com.honestyandpassion.ourcountry.MainActivity.EditProfileActivity
import com.honestyandpassion.ourcountry.MainActivity.PaymentActivity
import com.honestyandpassion.ourcountry.MainActivity.RegisterActivity
import com.honestyandpassion.ourcountry.MainActivity.SubCategoryActivity
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.item_category.view.*
import kotlinx.android.synthetic.main.item_selectcategory.view.*

class SelectUserTypeAdapter(val context: Context, val userTypeList:ArrayList<String>) : RecyclerView.Adapter<SelectUserTypeAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return userTypeList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectUserTypeAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_selectcategory, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SelectUserTypeAdapter.ViewHolder, position: Int) {
        holder.itemView.text_selectingcategory.text = userTypeList.get(position)
        holder.itemView.setOnClickListener{
            if(holder.itemView.text_selectingcategory.text!="전문판매자"){
            (context as Activity).finish()
            EditProfileActivity.userTypeText!!.text = userTypeList.get(position)
            }
            else{
                var intent = Intent(context, PaymentActivity::class.java)
                intent.putExtra("registerTitle","전문판매자변경신청")
                intent.putExtra("registerPrice","3000")
                intent.putExtra("registerId","")
                context.startActivity(intent)
            }
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: String) {

        }
    }

}
