package com.honestyandpassion.ourcountry.Fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.honestyandpassion.ourcountry.Adapter.FragmentCategoryAdapter

import com.honestyandpassion.ourcountry.R


class CategoryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_catergory, container, false)
        val categoryList = arrayListOf("농산물", "수산물", "축산물", "건강식품", "발효식품")
        var fragmentCategoryRV: RecyclerView = rootView.findViewById(R.id.rv_fragmentcategory)


        fragmentCategoryRV.setHasFixedSize(true)
        fragmentCategoryRV.layoutManager = GridLayoutManager(activity!!, 2)
        fragmentCategoryRV.adapter = FragmentCategoryAdapter(activity!!, categoryList)

        return rootView
    }
}
