package com.honestyandpassion.ourcountry.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.honestyandpassion.ourcountry.R

class ProductImagePagerAdapter(var context:Context, var imageList:ArrayList<Bitmap>) : PagerAdapter() {

    private var layoutInflater:LayoutInflater?=null

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view===`object`
    }

    override fun getCount(): Int {
        return imageList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater!!.inflate(R.layout.item_product_image_pager, null)
        var image=view.findViewById<ImageView>(R.id.img_product)

        image.setImageBitmap(imageList[position])
        val vp = container as ViewPager
        vp.addView(view , position)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }
}