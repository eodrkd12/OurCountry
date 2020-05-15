package com.honestyandpassion.ourcountry.Fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.Adapter.CategoryAdapter
import com.honestyandpassion.ourcountry.Item.Category
import com.honestyandpassion.ourcountry.Object.VolleyService

import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        val categoryList = arrayListOf("농산물", "수산물", "축산물", "건강식품", "발효식품")
        var categoryRV:RecyclerView = rootView.findViewById(R.id.rv_category)

        categoryRV.setHasFixedSize(true)
        categoryRV.layoutManager = LinearLayoutManager(activity!!, RecyclerView.HORIZONTAL, false)
        categoryRV.adapter = CategoryAdapter(activity!!, categoryList)

        return rootView
    }
}
