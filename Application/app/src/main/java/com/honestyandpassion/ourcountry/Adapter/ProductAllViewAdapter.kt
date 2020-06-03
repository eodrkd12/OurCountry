package com.honestyandpassion.ourcountry.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.honestyandpassion.ourcountry.Item.PreviewItem
import com.honestyandpassion.ourcountry.Item.Product
import com.honestyandpassion.ourcountry.MainActivity.ProductActivity
import com.honestyandpassion.ourcountry.Object.ImageManager
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.item_product.view.*
import org.json.JSONObject

class ProductAllViewAdapter(val context: Context, val productList:ArrayList<PreviewItem>) : RecyclerView.Adapter<ProductAllViewAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAllViewAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_search_product, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductAllViewAdapter.ViewHolder, position: Int) {
        var product=productList.get(position)
        holder.itemView.text_productregistertitle.text = product.registerTitle
        holder.itemView.text_productregisterprice.text = product.productPrice + "원"
        holder.itemView.text_productregistercondition.text = product.productStatus
        VolleyService.getProductImageReq(product.registerId!!,context,{success ->
            var array=success
            for(i in 0..array!!.length()-1){
                var json=array[i] as JSONObject
                var bitmap=ImageManager.StringToBitmap(json.getString("product_image"))
                product.imageList!!.add(bitmap!!)
            }
            holder.itemView.image_productimage.setImageBitmap(productList.get(position).imageList!![0])
        })

        holder.itemView.setOnClickListener {
            var intent= Intent(context,ProductActivity::class.java)

            intent.putExtra("register_id", product.registerId!!)
            ProductActivity.imageList.clear()
            for(i in 0..product.imageList!!.size-1){
                ProductActivity.imageList.add(product.imageList!![i])
            }
            ContextCompat.startActivity(context, intent, null)
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: String) {

        }
    }
}
