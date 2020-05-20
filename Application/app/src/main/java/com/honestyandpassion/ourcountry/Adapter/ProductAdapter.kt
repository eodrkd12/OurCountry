package com.honestyandpassion.ourcountry.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.honestyandpassion.ourcountry.Item.Product
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.item_product.view.*

class ProductAdapter(val context: Context, val productList:ArrayList<Product>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        holder.itemView.text_productregistertitle.text = productList.get(position).registerTitle
        holder.itemView.text_productregisterprice.text = productList.get(position).productPrice
        holder.itemView.text_productregistercondition.text = productList.get(position).productStatus
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: String) {

        }
    }
}
