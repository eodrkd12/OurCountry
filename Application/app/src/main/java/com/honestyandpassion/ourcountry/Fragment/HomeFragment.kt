package com.honestyandpassion.ourcountry.Fragment


import android.content.pm.PackageManager
import android.location.Geocoder

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.LocationServices
import com.honestyandpassion.ourcountry.Adapter.CategoryAdapter

import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        val categoryList = arrayListOf("농산물", "수산물", "축산물", "건강식품", "발효식품")
        var categoryRV:RecyclerView = rootView.findViewById(R.id.rv_category)

        initLocation()

        categoryRV.setHasFixedSize(true)
        categoryRV.layoutManager = LinearLayoutManager(activity!!, RecyclerView.HORIZONTAL, false)
        categoryRV.adapter = CategoryAdapter(activity!!, categoryList)

        return rootView
    }
    private  fun initLocation() {

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
        var fusedLocationClient=LocationServices.getFusedLocationProviderClient(activity!!)
        fusedLocationClient.lastLocation.addOnSuccessListener {location->
            if(location == null){
                Toast.makeText(activity,"null", Toast.LENGTH_SHORT).show()
            }else{
                var geocoder = Geocoder(activity)
               var list=geocoder.getFromLocation(location.latitude,location.longitude,1)
                var finallist= list!!.get(0).getAddressLine(0).split(" ")
                text_currentlocation.text=finallist.get(1)+" "+finallist.get(2)+" "+finallist.get(3)

            }
        }
            .addOnFailureListener{

            }

    }

}
