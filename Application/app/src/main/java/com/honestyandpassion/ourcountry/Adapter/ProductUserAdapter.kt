package com.honestyandpassion.ourcountry.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.honestyandpassion.ourcountry.R

class ProductUserAdapter(val context: Context, val productList:ArrayList<String>) : RecyclerView.Adapter<ProductUserAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductUserAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_product_user, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductUserAdapter.ViewHolder, position: Int) {

    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: String) {

        }
    }
}
