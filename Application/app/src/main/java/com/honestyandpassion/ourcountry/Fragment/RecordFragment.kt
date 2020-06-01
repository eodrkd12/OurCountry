package com.honestyandpassion.ourcountry.Fragment

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.honestyandpassion.ourcountry.Adapter.RecordAdapter
import com.honestyandpassion.ourcountry.Class.UserInfo
import com.honestyandpassion.ourcountry.Item.Product
import com.honestyandpassion.ourcountry.Object.VolleyService

import com.honestyandpassion.ourcountry.R
import org.json.JSONObject


class RecordFragment : Fragment() {

    var productList = ArrayList<com.honestyandpassion.ourcountry.Item.View>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_record, container, false)
        var recordRV: RecyclerView = rootView.findViewById(R.id.rv_record)

        VolleyService.getViewReq(UserInfo.ID, activity!!, {success->
            productList.clear()
            var array = success
            for(i in 0..array!!.length()-1){
                var json=array[i] as JSONObject
                var view= com.honestyandpassion.ourcountry.Item.View(json.getString("user_id"),
                    json.getInt("register_id"),
                    json.getString("view_date"),
                    json.getString("register_title"),
                    ArrayList<Bitmap>())
                productList.add(view)
            }
            recordRV.setHasFixedSize(true)
            recordRV.layoutManager = LinearLayoutManager(activity!!, RecyclerView.VERTICAL, false)
            recordRV.adapter = RecordAdapter(activity!!, productList)
        })

        return rootView
    }

}
