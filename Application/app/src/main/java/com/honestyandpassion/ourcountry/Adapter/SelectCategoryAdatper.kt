package com.honestyandpassion.ourcountry.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.honestyandpassion.ourcountry.IntroActivity.SelectCategoryActivity
import com.honestyandpassion.ourcountry.IntroActivity.SelectSubCategoryActivity
import com.honestyandpassion.ourcountry.Item.Category
import com.honestyandpassion.ourcountry.MainActivity.RegisterActivity
import com.honestyandpassion.ourcountry.MainActivity.SubCategoryActivity
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.item_category.view.*
import kotlinx.android.synthetic.main.item_selectcategory.view.*

class SelectCategoryAdapter(val context: Context, val categoryList:ArrayList<String>) : RecyclerView.Adapter<SelectCategoryAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectCategoryAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_selectcategory, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SelectCategoryAdapter.ViewHolder, position: Int) {
        holder.itemView.text_selectingcategory.text = categoryList.get(position)
        if(categoryList.get(position)=="농산물"){
            holder.itemView.setOnClickListener {
                RegisterActivity.categoryText!!.text = categoryList.get(position)
                startActivity(categoryList.get(position))
            }
        }
        else if(categoryList.get(position)=="수산물"){
            holder.itemView.setOnClickListener {
                RegisterActivity.categoryText!!.text = categoryList.get(position)
                startActivity(categoryList.get(position))
            }
        }
        else if(categoryList.get(position)=="축산물"){
            holder.itemView.setOnClickListener {
                RegisterActivity.categoryText!!.text = categoryList.get(position)
                startActivity(categoryList.get(position))
            }
        }
        else if(categoryList.get(position)=="건강식품"){
            holder.itemView.setOnClickListener {
                RegisterActivity.categoryText!!.text = categoryList.get(position)
                startActivity(categoryList.get(position))
            }
        }
        else if(categoryList.get(position)=="발효식품") {
            holder.itemView.setOnClickListener {
                RegisterActivity.categoryText!!.text = categoryList.get(position)
                startActivity(categoryList.get(position))
            }
        }
        else {
            holder.itemView.setOnClickListener{
                RegisterActivity.subCategoryText!!.text = categoryList.get(position)
                (context as Activity).finish()
            }
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: String) {

        }
    }

    fun startActivity(categoryType:String) {
        (context as Activity).finish()
        val intent = Intent(context, SelectSubCategoryActivity::class.java)
        intent.putExtra("categoryType", categoryType)
        context.startActivity(intent)
    }
}
