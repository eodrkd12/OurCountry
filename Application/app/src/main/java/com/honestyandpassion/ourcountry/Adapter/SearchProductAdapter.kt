package com.honestyandpassion.ourcountry.Adapter

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.honestyandpassion.ourcountry.Item.Product
import com.honestyandpassion.ourcountry.Object.ImageManager
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.item_product.view.*
import org.json.JSONObject

class SearchProductAdapter(val context: Context, val productList:ArrayList<Product>) : RecyclerView.Adapter<SearchProductAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchProductAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_search_product, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchProductAdapter.ViewHolder, position: Int) {
        holder.itemView.text_productregistertitle.text = productList.get(position).registerTitle
        holder.itemView.text_productregisterprice.text = productList.get(position).productPrice
        holder.itemView.text_productregistercondition.text = productList.get(position).productStatus
        VolleyService.getProductImageReq(productList.get(position).registerId!!,context,{success ->
            var array=success
            for(i in 0..array!!.length()-1){
                var json=array[i] as JSONObject
                Log.d("test",json.toString())
                var bitmap=ImageManager.StringToBitmap(json.getString("product_image"))
                productList.get(position).imageList!!.add(bitmap!!)
            }
            holder.itemView.image_productimage.setImageBitmap(productList.get(position).imageList!![0])
        })


        var displayMetrics: DisplayMetrics = DisplayMetrics()
        (holder.itemView.getContext() as Activity).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics) // 화면의 가로길이를 구함
        var width = displayMetrics.widthPixels / 2
        holder.itemView.getLayoutParams().width = width
        holder.itemView.requestLayout()
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: String) {

        }
    }
}
