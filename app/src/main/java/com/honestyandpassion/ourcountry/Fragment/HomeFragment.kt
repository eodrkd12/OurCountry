package com.honestyandpassion.ourcountry.Fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.commit.Adapter.CategoryAdapter

import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        // Inflate the layout for this fragment
        var categoryRV:RecyclerView = rootView.findViewById(R.id.rv_category)

        categoryRV.setHasFixedSize(true)
        categoryRV.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        categoryRV.adapter = CategoryAdapter(activity!!)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}
