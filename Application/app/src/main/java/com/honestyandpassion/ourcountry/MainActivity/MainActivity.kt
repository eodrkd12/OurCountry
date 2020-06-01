package com.honestyandpassion.ourcountry.MainActivity

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.content.pm.PackageManager
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.honestyandpassion.ourcountry.Class.UserInfo
import com.google.android.material.navigation.NavigationView
import com.honestyandpassion.ourcountry.Class.DialogMsg
import com.honestyandpassion.ourcountry.Fragment.*
import com.honestyandpassion.ourcountry.IntroActivity.SettingActivity
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_drawerlayout.*
import kotlinx.android.synthetic.main.notification_layout.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    var dialogMsg: DialogMsg? = null
    var test: Drawable? = null
    var bottomNavigationView: BottomNavigationView? = null
    var homeFragment: Fragment? = null
    var categoryFragment : Fragment? = null
    var mypageFragment : Fragment? = null
    var messageFragment : Fragment? = null


    private  val PermissinCode =100

    private val requiredPermission = arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkPermissions()
        dialogMsg = DialogMsg(this)

        setContentView(R.layout.activity_main_drawerlayout)
        setSupportActionBar(toolbar_main)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        bottomNavigationView = findViewById(R.id.bnv_main)
        bottomNavigationView!!.setOnNavigationItemSelectedListener(navListener)
        navigation_view.setNavigationItemSelectedListener(drawListener)

        test = getResources().getDrawable(R.drawable.baseline_menu_grey_24)
        supportActionBar?.setHomeAsUpIndicator(test)


        if (savedInstanceState == null) {
            homeFragment = HomeFragment()
            supportFragmentManager.beginTransaction().add(R.id.frame_main, homeFragment!!).commit()
        }

        btn_register.setOnClickListener {
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkPermissions(){
        val rejectedPermissionList = ArrayList<String>()

        for(permission in requiredPermission){
            if(ContextCompat.checkSelfPermission(this,permission)!=PackageManager.PERMISSION_GRANTED) {
                rejectedPermissionList.add(permission)
            }
        }
        if(rejectedPermissionList.isNotEmpty()){
            val array = arrayOfNulls<String>(rejectedPermissionList.size)
            ActivityCompat.requestPermissions(this,rejectedPermissionList.toArray(array),PermissinCode)
        }
    }


    //네비게이션 드로어 클릭이벤트 넣어야함
    private val drawListener = NavigationView.OnNavigationItemSelectedListener {
        bottomNavigationView = findViewById(R.id.bnv_main)
        when (it.itemId) {
            R.id.nav_home -> {
                if(homeFragment == null) {
                    homeFragment = HomeFragment()
                    supportFragmentManager.beginTransaction().add(R.id.frame_main, homeFragment!!).commit()
                }

                if(homeFragment != null) supportFragmentManager.beginTransaction().show(homeFragment!!).commit()
                if(categoryFragment != null) supportFragmentManager.beginTransaction().hide(categoryFragment!!).commit()
                if(mypageFragment != null) supportFragmentManager.beginTransaction().hide(mypageFragment!!).commit()
                if(messageFragment != null) supportFragmentManager.beginTransaction().hide(messageFragment!!).commit()

                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFFF")))
                test!!.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP)
                image_notification.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP)

                bottomNavigationView!!.menu.findItem(R.id.bnv_main_home).setChecked(true)
                drawerLayout.closeDrawers()
            }
            R.id.nav_category -> {
                if(categoryFragment == null) {
                    categoryFragment = CategoryFragment()
                    supportFragmentManager.beginTransaction().add(R.id.frame_main, categoryFragment!!).commit()
                }

                if(homeFragment != null) supportFragmentManager.beginTransaction().hide(homeFragment!!).commit()
                if(categoryFragment != null) supportFragmentManager.beginTransaction().show(categoryFragment!!).commit()
                if(mypageFragment != null) supportFragmentManager.beginTransaction().hide(mypageFragment!!).commit()
                if(messageFragment != null) supportFragmentManager.beginTransaction().hide(messageFragment!!).commit()

                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#D2232A")))
                test!!.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP)
                image_notification.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP)
                supportActionBar?.setTitle("카테고리")
                toolbar_main.setTitleTextColor(Color.WHITE)

              bottomNavigationView!!.menu.findItem(R.id.bnv_main_category).setChecked(true)
                drawerLayout.closeDrawers()

            }
            R.id.nav_latest -> {
                var intent = Intent(this, ProductAllViewActivity::class.java)
                intent.putExtra("clickedText", "최근등록된상품")
                startActivity(intent)
                drawerLayout.closeDrawers()
            }
            R.id.nav_popular -> {
                var intent = Intent(this, ProductAllViewActivity::class.java)
                intent.putExtra("clickedText", "인기상품")
                startActivity(intent)
                drawerLayout.closeDrawers()
            }
            R.id.nav_profile -> {
                if(mypageFragment == null) {
                    mypageFragment = MypageFragment()
                    supportFragmentManager.beginTransaction().add(R.id.frame_main, mypageFragment!!).commit()
                }
                if(homeFragment != null) supportFragmentManager.beginTransaction().hide(homeFragment!!).commit()
                if(categoryFragment != null) supportFragmentManager.beginTransaction().hide(categoryFragment!!).commit()
                if(mypageFragment != null) supportFragmentManager.beginTransaction().show(mypageFragment!!).commit()
                if(messageFragment != null) supportFragmentManager.beginTransaction().hide(messageFragment!!).commit()


                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#D2232A")))
                test!!.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP)
                image_notification.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP)
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
                var intent = Intent(this,SettingActivity::class.java)
                startActivity(intent)
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
        if (UserInfo.TYPE == "판매자" || UserInfo.TYPE=="전문판매자") btn_register.visibility = View.VISIBLE
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
                if(homeFragment == null) {
                    homeFragment = HomeFragment()
                    supportFragmentManager.beginTransaction().add(R.id.frame_main, homeFragment!!).commit()
                }

                if(homeFragment != null) supportFragmentManager.beginTransaction().show(homeFragment!!).commit()
                if(categoryFragment != null) supportFragmentManager.beginTransaction().hide(categoryFragment!!).commit()
                if(mypageFragment != null) supportFragmentManager.beginTransaction().hide(mypageFragment!!).commit()
                if(messageFragment != null) supportFragmentManager.beginTransaction().hide(messageFragment!!).commit()

                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFFF")))
                test!!.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP)
                image_notification.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP)
                btn_register.visibility=View.VISIBLE

                return@OnNavigationItemSelectedListener true
            }
            R.id.bnv_main_category -> {
                if(categoryFragment == null) {
                    categoryFragment = CategoryFragment()
                    supportFragmentManager.beginTransaction().add(R.id.frame_main, categoryFragment!!).commit()
                }

                if(homeFragment != null) supportFragmentManager.beginTransaction().hide(homeFragment!!).commit()
                if(categoryFragment != null) supportFragmentManager.beginTransaction().show(categoryFragment!!).commit()
                if(mypageFragment != null) supportFragmentManager.beginTransaction().hide(mypageFragment!!).commit()
                if(messageFragment != null) supportFragmentManager.beginTransaction().hide(messageFragment!!).commit()

                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#D2232A")))
                test!!.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP)
                image_notification.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP)
                supportActionBar?.setTitle("카테고리")
                toolbar_main.setTitleTextColor(Color.WHITE)
                btn_register.visibility=View.INVISIBLE
                return@OnNavigationItemSelectedListener true
            }
            R.id.bnv_main_mypage -> {
                if(mypageFragment == null) {
                    mypageFragment = MypageFragment()
                    supportFragmentManager.beginTransaction().add(R.id.frame_main, mypageFragment!!).commit()
                }
                if(homeFragment != null) supportFragmentManager.beginTransaction().hide(homeFragment!!).commit()
                if(categoryFragment != null) supportFragmentManager.beginTransaction().hide(categoryFragment!!).commit()
                if(mypageFragment != null) supportFragmentManager.beginTransaction().show(mypageFragment!!).commit()
                if(messageFragment != null) supportFragmentManager.beginTransaction().hide(messageFragment!!).commit()


                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#D2232A")))
                test!!.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP)
                image_notification.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP)
                btn_register.visibility=View.INVISIBLE
                return@OnNavigationItemSelectedListener true
            }
            R.id.bnv_main_message -> {
                if(messageFragment == null) {
                    messageFragment = MessageFragment()
                    supportFragmentManager.beginTransaction().add(R.id.frame_main, messageFragment!!).commit()
                }
                if(homeFragment != null) supportFragmentManager.beginTransaction().hide(homeFragment!!).commit()
                if(categoryFragment != null) supportFragmentManager.beginTransaction().hide(categoryFragment!!).commit()
                if(mypageFragment != null) supportFragmentManager.beginTransaction().hide(mypageFragment!!).commit()
                if(messageFragment != null) supportFragmentManager.beginTransaction().show(messageFragment!!).commit()

                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#D2232A")))
                test!!.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP)
                image_notification.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP)
                btn_register.visibility=View.INVISIBLE
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater = getMenuInflater()
        inflater.inflate(R.menu.menu_alart, menu)
        //색변경
        if (menu != null) {
            for (i in 0 until menu.size()) {
                val notiImageView = menu.getItem(i).actionView.findViewById<ImageView>(R.id.image_notification)
                if (notiImageView != null) {
                    notiImageView!!.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP)
                }
            }
        }
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


}
