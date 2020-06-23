package com.honestyandpassion.ourcountry.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.honestyandpassion.ourcountry.Item.ReviewItem
import com.honestyandpassion.ourcountry.MainActivity.ProductActivity
import com.honestyandpassion.ourcountry.Object.ImageManager
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.item_product.view.*
import kotlinx.android.synthetic.main.item_review.view.*
import org.json.JSONObject

class ReviewAdapter(val context: Context, val reviewList:ArrayList<ReviewItem>): RecyclerView.Adapter<ReviewAdapter.ViewHolder>()  {
    override fun getItemCount(): Int {
       return reviewList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReviewAdapter.ViewHolder, position: Int) {

        var review=reviewList.get(position)
        holder.itemView.text_user.text=review.user_id
        holder.itemView.text_review.text=review.review_content
        holder.itemView.text_date.text=review.review_date?.substring(0,11)

        holder.itemView.setOnClickListener {

        }

    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun bindItems(data: String) {

        }
    }
}