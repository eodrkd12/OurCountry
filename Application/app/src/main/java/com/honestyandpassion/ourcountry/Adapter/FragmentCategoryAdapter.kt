package com.honestyandpassion.ourcountry.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.honestyandpassion.ourcountry.Item.Category
import com.honestyandpassion.ourcountry.MainActivity.SubCategoryActivity
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.item_category.view.*
import kotlinx.android.synthetic.main.item_fragment_category.view.*

class FragmentCategoryAdapter(val context: Context, val categoryList:ArrayList<String>) : RecyclerView.Adapter<FragmentCategoryAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FragmentCategoryAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_fragment_category, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FragmentCategoryAdapter.ViewHolder, position: Int) {
        if(categoryList.get(position)=="농산물"){
            holder.itemView.text_category.text = categoryList.get(position)
            holder.itemView.image_category_background.setImageResource(R.drawable.icon_grain)
            holder.itemView.setOnClickListener {
                startActivity(categoryList.get(position))
            }
        }
        else if(categoryList.get(position)=="수산물"){
            holder.itemView.text_category.text = categoryList.get(position)
            holder.itemView.image_category_background.setImageResource(R.drawable.icon_aquatic)
            holder.itemView.setOnClickListener {
                startActivity(categoryList.get(position))
            }
        }
        else if(categoryList.get(position)=="축산물"){
            holder.itemView.text_category.text = categoryList.get(position)
            holder.itemView.image_category_background.setImageResource(R.drawable.icon_meat)
            holder.itemView.setOnClickListener {
                startActivity(categoryList.get(position))
            }
        }
        else if(categoryList.get(position)=="건강식품"){
            holder.itemView.text_category.text = categoryList.get(position)
            holder.itemView.image_category_background.setImageResource(R.drawable.icon_health)
            holder.itemView.setOnClickListener {
                startActivity(categoryList.get(position))
            }
        }
        else if(categoryList.get(position)=="발효식품") {
            holder.itemView.text_category.text = categoryList.get(position)
            holder.itemView.image_category_background.setImageResource(R.drawable.icon_fermented)
            holder.itemView.setOnClickListener {
                startActivity(categoryList.get(position))
            }
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: String) {

        }
    }

    fun startActivity(categoryType:String) {
        val intent = Intent(context, SubCategoryActivity::class.java)
        intent.putExtra("categoryType", categoryType)
        context.startActivity(intent)
    }
}
