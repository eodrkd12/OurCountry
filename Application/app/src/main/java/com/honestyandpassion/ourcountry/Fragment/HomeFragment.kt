package com.honestyandpassion.ourcountry.Fragment


import android.content.Intent
import android.graphics.Bitmap
import android.content.pm.PackageManager
import android.location.Geocoder

import android.os.Bundle
import android.os.Handler
import android.os.Message

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.LocationServices
import com.honestyandpassion.ourcountry.Adapter.CategoryAdapter
import com.honestyandpassion.ourcountry.Adapter.ProductAdapter
import com.honestyandpassion.ourcountry.Item.Product
import com.honestyandpassion.ourcountry.MainActivity.CategoryActivity
import com.honestyandpassion.ourcountry.MainActivity.ProductAllViewActivity
import com.honestyandpassion.ourcountry.MainActivity.SearchActivity
import com.honestyandpassion.ourcountry.Object.VolleyService

import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONObject

class HomeFragment : Fragment() {

    companion object{
        var HANDLER:Handler?=null
        var recentProductArrayList=ArrayList<Product>()
        var popularProductArrayList=ArrayList<Product>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        val categoryList = arrayListOf("농산물", "수산물", "축산물", "건강식품", "발효식품")
        var categoryRV:RecyclerView = rootView.findViewById(R.id.rv_category)
        var recentProductRV:RecyclerView=rootView.findViewById(R.id.rv_recentproduct)
        var popularProductRV:RecyclerView=rootView.findViewById(R.id.rv_popularproduct)
        var searchBtn: ImageButton = rootView.findViewById(R.id.btn_homesearch)
        var recentTextView: TextView = rootView.findViewById(R.id.text_recentallview)
        var popularTextView : TextView = rootView.findViewById(R.id.text_popularallview)
        var categoryTextView : TextView = rootView.findViewById(R.id.text_categoryallview)

        initLocation()

        categoryRV.setHasFixedSize(true)
        categoryRV.layoutManager = LinearLayoutManager(activity!!, RecyclerView.HORIZONTAL, false)
        categoryRV.adapter = CategoryAdapter(activity!!, categoryList)

        searchBtn.setOnClickListener {
            var intent = Intent(activity!!, SearchActivity::class.java)
            intent.putExtra("searchText", edit_homesearch.text.toString())
            startActivity(intent)
        }

        recentTextView.setOnClickListener {
            var intent = Intent(activity!!, ProductAllViewActivity::class.java)
            intent.putExtra("clickedText", "최근등록된상품")
            startActivity(intent)
        }

        popularTextView.setOnClickListener {
            var intent = Intent(activity!!, ProductAllViewActivity::class.java)
            intent.putExtra("clickedText", "인기상품")
            startActivity(intent)
        }

        categoryTextView.setOnClickListener {
            var intent = Intent(activity!!, CategoryActivity::class.java)
            startActivity(intent)
        }

        VolleyService.recentRegisterReq(activity!!,{ success ->
            recentProductArrayList.clear()
            var array=success
            for(i in 0..array!!.length()-1){
                var json=array[i] as JSONObject
                var product=Product(json.getInt("register_id"),
                    json.getString("user_id"),
                    json.getString("register_title"),
                    json.getString("product_category"),
                    json.getString("product_subcategory"),
                    json.getString("product_type"),
                    json.getString("product_status"),
                    json.getString("product_brand"),
                    json.getString("product_price"),
                    json.getInt("seller_store"),
                    json.getString("register_content"),
                    json.getString("trade_option"),
                    json.getString("seller_address"),
                    json.getString("register_date"),
                    json.getInt("register_like"),
                    json.getInt("register_view"),
                    json.getString("user_nickname"),
                    ArrayList<Bitmap>())

                recentProductArrayList.add(product)
            }
            recentProductRV.setHasFixedSize(true)
            recentProductRV.layoutManager=LinearLayoutManager(activity!!,RecyclerView.HORIZONTAL,false)
            recentProductRV.adapter= ProductAdapter(activity!!,recentProductArrayList)
        })

        VolleyService.popularRegisterReq(activity!!,{ success ->
            popularProductArrayList.clear()
            var array=success
            for(i in 0..array!!.length()-1){
                var json=array[i] as JSONObject
                var product=Product(json.getInt("register_id"),
                    json.getString("user_id"),
                    json.getString("register_title"),
                    json.getString("product_category"),
                    json.getString("product_subcategory"),
                    json.getString("product_type"),
                    json.getString("product_status"),
                    json.getString("product_brand"),
                    json.getString("product_price"),
                    json.getInt("seller_store"),
                    json.getString("register_content"),
                    json.getString("trade_option"),
                    json.getString("seller_address"),
                    json.getString("register_date"),
                    json.getInt("register_like"),
                    json.getInt("register_view"),
                    json.getString("user_nickname"),
                    ArrayList<Bitmap>())
                popularProductArrayList.add(product)
            }
            popularProductRV.setHasFixedSize(true)
            popularProductRV.layoutManager=LinearLayoutManager(activity!!,RecyclerView.HORIZONTAL,false)
            popularProductRV.adapter= ProductAdapter(activity!!,popularProductArrayList)
        })

        HANDLER = object : Handler() {
            override fun handleMessage(msg: Message?) {
                when (msg!!.what) {
                    0 -> {
                        refresh()
                    }
                }
            }
        }

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
        var fusedLocationClient= LocationServices.getFusedLocationProviderClient(activity!!)
        fusedLocationClient.lastLocation.addOnSuccessListener {location->
            if(location == null){
                Toast.makeText(activity,"null", Toast.LENGTH_SHORT).show()
            }else{
                var geocoder = Geocoder(activity)
                var list=geocoder.getFromLocation(location.latitude,location.longitude,10)
                var finallist= list!!.get(0).getAddressLine(0).split(" ")
                text_currentlocation.text=finallist.get(1)+" "+finallist.get(2)+" "+finallist.get(3)
            }
        }
            .addOnFailureListener{

        }

    }

    fun refresh(){
        var transaction=fragmentManager!!.beginTransaction()
        transaction.detach(this).attach(this).commitAllowingStateLoss()
    }

}
