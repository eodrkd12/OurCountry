package com.honestyandpassion.ourcountry.MainActivity

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import com.honestyandpassion.ourcountry.Class.ToolbarSetting
import com.honestyandpassion.ourcountry.Item.Product
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.R
import org.json.JSONArray

class SearchActivity : ToolbarSetting() {

    var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        var toolbar : Toolbar = findViewById(R.id.toolbar_search)
        var intent = intent
        var searchText: String = intent.getStringExtra("searchText")

        toolbarBinding(toolbar, searchText)
    }

    inner class AsyncTask: android.os.AsyncTask<String, Long, ArrayList<Product>>() {
        override fun onPreExecute() {
            super.onPreExecute()
            dialog = Dialog(this@SearchActivity, R.style.loading_dialog_style)
            var pb = ProgressBar(this@SearchActivity)

            dialog!!.addContentView(pb, LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT))
            dialog!!.show()
        }

        override fun doInBackground(vararg p0: String?): ArrayList<Product> {
            var searchArray : JSONArray? = null
            VolleyService.searchRegisterReq(p0[0]!!, this@SearchActivity, {success->
                searchArray = success
            })
            return menuList!!
        }

        override fun onProgressUpdate(vararg values: Long?) {
            super.onProgressUpdate(*values)
        }

        override fun onPostExecute(result: ArrayList<Product>?) {
            super.onPostExecute(result)

            if(menuList.size < 5)
            {
                menuRV.adapter = MenuPreviewAdapter(result!!, menuList.size-1)
                menuRV.setHasFixedSize(true)
                menuRV.layoutManager = LinearLayoutManager(this@InformActivity, RecyclerView.VERTICAL, false)
            }
            else
            {
                menuRV.adapter = MenuPreviewAdapter(result!!, 5)
                menuRV.setHasFixedSize(true)
                menuRV.layoutManager = LinearLayoutManager(this@InformActivity, RecyclerView.VERTICAL, false)
            }

            dialog!!.dismiss()
        }


    }
}
