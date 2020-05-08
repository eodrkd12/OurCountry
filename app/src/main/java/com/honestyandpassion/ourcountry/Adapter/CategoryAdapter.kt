package com.example.commit.Adapter

import android.content.Context
import android.content.Intent
import android.os.Message
import android.util.Log
//import android.support.v7.app.AppCompatActivity
//import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.honestyandpassion.ourcountry.MainActivity.SubCategoryActivity
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.item_category.view.*
import org.json.JSONArray
import java.util.logging.Handler

class CategoryAdapter(val context: Context) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    val categoryList = arrayListOf("농산물", "수산물", "축산물", "건강식품", "발효식품")

    fun startActivity(categoryType:String) {
        val intent = Intent(context, SubCategoryActivity::class.java)
        intent.putExtra("categoryType", categoryType)
        context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return categoryList.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        if(categoryList.get(position)=="농산물"){
            holder.itemView.text_category_lable.text = categoryList.get(position)
            holder.itemView.btn_category.setImageResource(R.drawable.icon_grain)
            holder.itemView.setOnClickListener {
                startActivity(categoryList.get(position))
            }
        }
        else if(categoryList.get(position)=="수산물"){
            holder.itemView.btn_category.setImageResource(R.drawable.icon_aquatic)
            holder.itemView.setOnClickListener {
                startActivity(categoryList.get(position))
            }
        }
        else if(categoryList.get(position)=="축산물"){
            holder.itemView.btn_category.setImageResource(R.drawable.icon_meat)
            holder.itemView.setOnClickListener {
                startActivity(categoryList.get(position))
            }
        }
        else if(categoryList.get(position)=="건강식품"){
            holder.itemView.btn_category.setImageResource(R.drawable.icon_health)
            holder.itemView.setOnClickListener {
                startActivity(categoryList.get(position))
            }
        }
        else if(categoryList.get(position)=="발효식품") {
            holder.itemView.btn_category.setImageResource(R.drawable.icon_fermented)
            holder.itemView.setOnClickListener {
                startActivity(categoryList.get(position))
            }
        }

    }



    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: String) {

        }
    }

}
