package com.honestyandpassion.ourcountry.MainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.honestyandpassion.ourcountry.R

class ProductListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        var intent = intent
        var subCategoryType: String = intent.getStringExtra("subCategoryType")

        var toolbar: androidx.appcompat.widget.Toolbar = findViewById(com.honestyandpassion.ourcountry.R.id.toolbar_productlist)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(subCategoryType)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId)
        {
            android.R.id.home-> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
