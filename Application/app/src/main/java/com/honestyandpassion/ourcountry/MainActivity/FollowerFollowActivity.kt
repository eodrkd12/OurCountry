package com.honestyandpassion.ourcountry.MainActivity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.honestyandpassion.ourcountry.Adapter.FollowAdapter
import com.honestyandpassion.ourcountry.Class.ToolbarSetting
import com.honestyandpassion.ourcountry.Class.UserInfo
import com.honestyandpassion.ourcountry.Item.followItem
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_followerfollow.*
import kotlinx.android.synthetic.main.activity_product.*
import org.json.JSONObject

class FollowerFollowActivity: ToolbarSetting() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_followerfollow)

        var intent = intent
        var res=intent.getStringExtra("res")
        var followlist=ArrayList<followItem>()

        var toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_follow)

        when(res){
            "follower"->{
                toolbarBinding(toolbar, "팔로워")
                VolleyService.getFollow("following",UserInfo.ID,this,{success->
                    followlist.clear()
                    var array=success
                    for(i in 0..array!!.length()-1) {
                        var json = array[i] as JSONObject
                        var reslist = followItem(
                        json.getString("follower"),
                        "null",
                        json.getString("user_nickname"),
                            json.getString("user_id"),
                            json.getDouble("user_rating_average").toString(),
                            json.getDouble("user_rating_average").toFloat(),
                            json.getString("user_rating_count"),
                            json.getString("user_join_date"),
                            json.getString("sellercertification")
                        )
                        followlist.add(reslist)
                    }
                    activityBinding(followlist)
                })

            }
            "following"->{
                toolbarBinding(toolbar, "팔로잉")
                VolleyService.getFollow("follower",UserInfo.ID,this,{success->
                    followlist.clear()
                    var array=success
                    for(i in 0..array!!.length()-1){
                        var json = array[i] as JSONObject
                        var reslist=followItem(
                          "null",
                            json.getString("following"),
                            json.getString("user_nickname"),
                                    json.getString("user_id"),
                            json.getString("user_rating_average"),
                            json.getString("user_rating").toFloat(),
                            json.getString("user_rating_count"),
                            json.getString("user_join_date"),
                            json.getString("user_login_type")
                        )
                        followlist.add(reslist)
                    }
                    activityBinding(followlist)
                })
            }

        }
    }
    fun activityBinding(list:ArrayList<followItem>){
        if(list.size==0){
            image_emptyrecent.visibility= View.VISIBLE
            text_emptyrecent2.visibility=View.VISIBLE
        }
        else{
            rv_follow.setHasFixedSize(true)
            rv_follow.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
            rv_follow.adapter=FollowAdapter(this,list)

        }
    }
}