package com.honestyandpassion.ourcountry.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.honestyandpassion.ourcountry.Class.UserInfo
import com.honestyandpassion.ourcountry.Item.followItem
import com.honestyandpassion.ourcountry.MainActivity.PageActivity
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.item_follow.view.*


class FollowAdapter(val context: Context, val followlist:ArrayList<followItem>):RecyclerView.Adapter<FollowAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
       return  followlist.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowAdapter.ViewHolder {
    val itemView=LayoutInflater.from(parent.context).inflate(R.layout.item_follow,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FollowAdapter.ViewHolder, position: Int) {
        var follow=followlist.get(position)
        holder.itemView.text_nickname.text=follow.user_nickname

        holder.itemView.setOnClickListener{
            var intent = Intent(context,PageActivity::class.java)
            intent.putExtra("name",follow.user_nickname)
            intent.putExtra("id",follow.user_id)
            intent.putExtra("user_rating_average",follow.user_rating_average)
            intent.putExtra("user_rating",follow.user_rating)
            intent.putExtra("user_rating_count",follow.user_rating_count)
            intent.putExtra("user_join_date",follow.user_join_date!!.split('T')[0])
            intent.putExtra("sellercertification",follow.sellercertification)
            ContextCompat.startActivity(context, intent, null)
        }

    }
    class ViewHolder(val view: View):RecyclerView.ViewHolder(view){
        fun bindItems(data:String){

        }
    }
}