package com.honestyandpassion.ourcountry.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.honestyandpassion.ourcountry.Item.Product
import com.honestyandpassion.ourcountry.MainActivity.ProductActivity
import com.honestyandpassion.ourcountry.Object.ImageManager
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.item_product.view.*
import kotlinx.android.synthetic.main.item_record.view.*
import org.json.JSONObject
import java.time.format.DateTimeFormatter

class RecordAdapter(val context: Context, val productList:ArrayList<com.honestyandpassion.ourcountry.Item.View>) : RecyclerView.Adapter<RecordAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_record, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecordAdapter.ViewHolder, position: Int) {
        var product=productList.get(position)
        holder.itemView.text_recordtitle.text = product.registerTitle
        holder.itemView.text_recorddate.text = product.viewDate!!.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        VolleyService.getProductImageReq(product.registerId!!,context,{success ->
            var array=success
            for(i in 0..array!!.length()-1){
                var json=array[i] as JSONObject
                var bitmap=ImageManager.StringToBitmap(json.getString("product_image"))
                product.imageList!!.add(bitmap!!)
            }
            holder.itemView.image_record.setImageBitmap(productList.get(position).imageList!![0])
        })

        /*holder.itemView.setOnClickListener {
            var intent= Intent(context,ProductActivity::class.java)

            intent.putExtra("product",product)
            ProductActivity.imageList.clear()
            for(i in 0..product.imageList!!.size-1){
                ProductActivity.imageList.add(product.imageList!![i])
            }
            ContextCompat.startActivity(context, intent, null)
        }*/
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: String) {
        }
    }
}
