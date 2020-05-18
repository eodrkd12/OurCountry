package com.honestyandpassion.ourcountry.Fragment

import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.LocationServices
import com.honestyandpassion.ourcountry.Adapter.CategoryAdapter

import com.honestyandpassion.ourcountry.Item.Category
import com.honestyandpassion.ourcountry.Object.VolleyService

import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_product.view.*
import kotlinx.android.synthetic.main.activity_setting.*

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
    private fun initLocation() {

        if (ActivityCompat.checkSelfPermission(
                activity!!,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                activity!!,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        var fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location == null) {
                    Log.e(TAG, "location get fail")
                } else {
                    Log.d(TAG, "${location.latitude} , ${location.longitude}")
                    text_currentlocation.text=location.latitude.toString()+location.longitude.toString()

                }
            }
            .addOnFailureListener {
                Log.e(TAG, "location error is ${it.message}")
                it.printStackTrace()
            }
    }

}
