package com.honestyandpassion.ourcountry.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.honestyandpassion.ourcountry.IntroActivity.SelectCategoryActivity
import com.honestyandpassion.ourcountry.IntroActivity.SelectSubCategoryActivity
import com.honestyandpassion.ourcountry.Item.Category
import com.honestyandpassion.ourcountry.MainActivity.ProductListActivity
import com.honestyandpassion.ourcountry.MainActivity.RegisterActivity
import com.honestyandpassion.ourcountry.MainActivity.SubCategoryActivity
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.item_category.view.*
import kotlinx.android.synthetic.main.item_fragment_category.view.*
import kotlinx.android.synthetic.main.item_selectcategory.view.*

class SubCategoryAdapter(val context: Context, val categoryList:ArrayList<String>, val categoryType:String) : RecyclerView.Adapter<SubCategoryAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_fragment_category, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SubCategoryAdapter.ViewHolder, position: Int) {
        if(categoryType=="농산물")
        {
            var array = context.resources.obtainTypedArray(R.array.drawablegrain)
            holder.itemView.image_category_background.setImageResource(array.getResourceId(position,0))
            holder.itemView.imageView22.visibility=View.INVISIBLE
            holder.itemView.setOnClickListener{
                var intent = Intent(context, ProductListActivity::class.java)
                intent.putExtra("subCategoryType", categoryList.get(position))
                context.startActivity(intent)
            }
        }
        else if(categoryType=="수산물")
        {
            var array = context.resources.obtainTypedArray(R.array.drawableaquatic)
            holder.itemView.image_category_background.setImageResource(array.getResourceId(position,0))
            holder.itemView.imageView22.visibility=View.INVISIBLE
            holder.itemView.setOnClickListener{
                var intent = Intent(context, ProductListActivity::class.java)
                intent.putExtra("subCategoryType", categoryList.get(position))
                context.startActivity(intent)
            }
        }
        else if(categoryType=="축산물")
        {
            var array = context.resources.obtainTypedArray(R.array.drawablemeat)
            holder.itemView.image_category_background.setImageResource(array.getResourceId(position,0))
            holder.itemView.imageView22.visibility=View.INVISIBLE
            holder.itemView.setOnClickListener{
                var intent = Intent(context, ProductListActivity::class.java)
                intent.putExtra("subCategoryType", categoryList.get(position))
                context.startActivity(intent)
            }
        }
        else if(categoryType=="건강식품")
        {
            var array = context.resources.obtainTypedArray(R.array.drawablehealth)
            holder.itemView.image_category_background.setImageResource(array.getResourceId(position,0))
            holder.itemView.imageView22.visibility=View.INVISIBLE
            holder.itemView.setOnClickListener{
                var intent = Intent(context, ProductListActivity::class.java)
                intent.putExtra("subCategoryType", categoryList.get(position))
                context.startActivity(intent)
            }
        }
        else if(categoryType=="발효식품")
        {
            var array = context.resources.obtainTypedArray(R.array.drawablefermented)
            holder.itemView.image_category_background.setImageResource(array.getResourceId(position,0))
            holder.itemView.imageView22.visibility=View.INVISIBLE
            holder.itemView.setOnClickListener{
                var intent = Intent(context, ProductListActivity::class.java)
                intent.putExtra("subCategoryType", categoryList.get(position))
                context.startActivity(intent)
            }
        }
    }



    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: String) {

        }
    }



}
