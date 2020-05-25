package com.honestyandpassion.ourcountry.MainActivity

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.honestyandpassion.ourcountry.Class.UserInfo
import com.google.android.material.navigation.NavigationView
import com.honestyandpassion.ourcountry.Class.DialogMsg
import com.honestyandpassion.ourcountry.Adapter.ProductAdapter
import com.google.android.material.snackbar.Snackbar
import com.honestyandpassion.ourcountry.Fragment.CategoryFragment
import com.honestyandpassion.ourcountry.Fragment.HomeFragment
import com.honestyandpassion.ourcountry.Fragment.MessageFragment
import com.honestyandpassion.ourcountry.Fragment.MypageFragment
import com.honestyandpassion.ourcountry.Item.Product
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_drawerlayout.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var dialogMsg: DialogMsg? = null
    var test: Drawable? = null
    var bottomNavigationView: BottomNavigationView? = null
    var list: List<Address>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dialogMsg = DialogMsg(this)

        setContentView(R.layout.activity_main_drawerlayout)
        setSupportActionBar(toolbar_main)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bnv_main)
        bottomNavigationView!!.setOnNavigationItemSelectedListener(navListener)
        navigation_view.setNavigationItemSelectedListener(drawListener)

        test = getResources().getDrawable(R.drawable.baseline_menu_grey_24)
        supportActionBar?.setHomeAsUpIndicator(test)

        if (savedInstanceState == null) {
            val fragment = HomeFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_main, fragment, fragment.javaClass.simpleName).commit()
            supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFFF")))
        }

        btn_register.setOnClickListener {
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    //네비게이션 드로어 클릭이벤트 넣어야함
    private val drawListener = NavigationView.OnNavigationItemSelectedListener {
        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bnv_main)
        when (it.itemId) {
            R.id.nav_home -> {
                val fragment = HomeFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_main, fragment, fragment.javaClass.simpleName).commit()
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFFF")))
                test!!.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP)
                btn_register.visibility = View.VISIBLE
                bottomNavigationView!!.menu.findItem(R.id.bnv_main_home).setChecked(true)
                drawerLayout.closeDrawers()

            }
            R.id.nav_category -> {
                val fragment = CategoryFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_main, fragment, fragment.javaClass.simpleName).commit()
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#D2232A")))
                test!!.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP)
                supportActionBar?.setTitle("카테고리")
                btn_register.visibility = View.INVISIBLE
                toolbar_main.setTitleTextColor(Color.WHITE)
                bottomNavigationView!!.menu.findItem(R.id.bnv_main_category).setChecked(true)
                drawerLayout.closeDrawers()

            }
            R.id.nav_latest -> {
                drawerLayout.closeDrawers()
            }
            R.id.nav_popular -> {
                drawerLayout.closeDrawers()
            }
            R.id.nav_profile -> {
                val fragment = MypageFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_main, fragment, fragment.javaClass.simpleName).commit()
                btn_register.visibility = View.INVISIBLE
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#D2232A")))
                test!!.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP)
                bottomNavigationView!!.menu.findItem(R.id.bnv_main_mypage).setChecked(true)
                drawerLayout.closeDrawers()
            }
            R.id.nav_language -> {
                drawerLayout.closeDrawers()
            }
            R.id.nav_contact_us -> {
                drawerLayout.closeDrawers()
            }
            R.id.nav_setting -> {
                drawerLayout.closeDrawers()
            }
            R.id.nav_privacy_policy -> {
                drawerLayout.closeDrawers()
            }
            R.id.nav_rate_this_app -> {
                drawerLayout.closeDrawers()
            }


        }
        false
    }

    override fun onResume() {
        super.onResume()
        Log.d("test",UserInfo.TYPE)
        if (UserInfo.TYPE == "seller") btn_register.visibility = View.VISIBLE
        else btn_register.visibility = View.GONE

        //홈프래그먼트 새로고침
        /*var handler=HomeFragment.HANDLER
        var msg=handler!!.obtainMessage()
        msg.what=0
        handler.sendMessage(msg)*/
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers()
        } else {
            dialogMsg!!.showDialog("확인", "취소", "확인", "정말로 종료 하시겠습니까?")
            dialogMsg!!.show()
        }
    }
    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.bnv_main_home -> {
                val fragment = HomeFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_main, fragment, fragment.javaClass.simpleName).commit()
                btn_register.visibility = View.VISIBLE
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFFF")))
                test!!.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bnv_main_category -> {
                val fragment = CategoryFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_main, fragment, fragment.javaClass.simpleName).commit()
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#D2232A")))
                test!!.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP)
                supportActionBar?.setTitle("카테고리")
                toolbar_main.setTitleTextColor(Color.WHITE)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bnv_main_mypage -> {
                val fragment = MypageFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_main, fragment, fragment.javaClass.simpleName).commit()
                btn_register.visibility = View.INVISIBLE
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#D2232A")))
                test!!.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bnv_main_message -> {
                val fragment = MessageFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_main, fragment, fragment.javaClass.simpleName).commit()
                btn_register.visibility = View.INVISIBLE
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#D2232A")))
                test!!.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater = getMenuInflater()
        inflater.inflate(R.menu.menu_alart, menu)
        menu!!.add(0, 0, 0, "알림").setIcon(R.drawable.baseline_menu_grey_24)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //네비게이션 안 아이템 클릭시 (수정 중)
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                var intent = Intent(this, HomeFragment::class.java)
                startActivity(intent)
            }
        }
        return false
    }

    private fun initLocation() {

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        var fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener { Location ->
            if (Location == null) {
                Toast.makeText(this, "null", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this,
                    "${Location.latitude} , ${Location.longitude}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.addOnFailureListener {

        }
    }
}
