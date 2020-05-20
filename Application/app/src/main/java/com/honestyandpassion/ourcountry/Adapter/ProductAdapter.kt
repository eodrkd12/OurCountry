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

class ProductAdapter(val context: Context, val productList:ArrayList<String>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {

    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: String) {

        }
    }
}
