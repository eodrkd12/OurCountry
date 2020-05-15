package com.honestyandpassion.ourcountry.MainActivity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.honestyandpassion.ourcountry.IntroActivity.SelectCategoryActivity
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_register.*
import java.time.LocalDateTime
import java.util.*

class RegisterActivity : AppCompatActivity() {

    var productType : String = ""
    var productStatus: String = ""
    var tradeOption: String = ""
    var sellerStore: Int = 0

    companion object{
        var categoryText : TextView?= null
        var subCategoryText : TextView? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        categoryText = findViewById(R.id.text_selectcategory)
        subCategoryText = findViewById(R.id.text_selectsubcategory)

        var registerDate : Date = Calendar.getInstance().getTime()

        var toolbar: androidx.appcompat.widget.Toolbar = findViewById(com.honestyandpassion.ourcountry.R.id.toolbar_register)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("상품 등록")

        layout_selectcategory.setOnClickListener{
            var intent = Intent(this, SelectCategoryActivity::class.java)
            startActivity(intent)
        }

        radiogroup_producttype.setOnCheckedChangeListener {radioGroup, i ->
            when(i) {
                R.id.radio_typeoption1 -> productType = "산지직송"
                R.id.radio_typeoption2 -> productType = "팝니다"
                R.id.radio_typeoption3 -> productType = "삽니다"
                R.id.radio_typeoption4 -> productType = "무료나눔"
                R.id.radio_typeoption5 -> productType = "교환신청"
            }
        }

        radiogroup_tradeoption.setOnCheckedChangeListener {radioGroup, i ->
            when(i) {
                R.id.check_tradeoption1 -> tradeOption = "택배"
                R.id.check_tradeoption2 -> tradeOption = "무료배송"
                R.id.check_tradeoption3 -> tradeOption = "직거래"
            }
        }

        radiogroup_productstatus.setOnCheckedChangeListener {radioGroup, i ->
            when(i) {
                R.id.radio_statusoption1 -> productStatus = "신선식품"
                R.id.radio_statusoption2 -> productStatus = "가공식품"
                R.id.radio_statusoption3 -> productStatus = "새상품"
                R.id.radio_statusoption4 -> productStatus = "중고"
            }
        }


        check_store.setOnCheckedChangeListener { compoundButton, b ->
            if(b) {
                sellerStore = 1
            }
            else{
                sellerStore = 0
            }
        }

        btn_registercomplete.setOnClickListener {
            if(check_tradeoption1.isChecked == true) tradeOption += check_tradeoption1.text
            if(check_tradeoption2.isChecked == true) tradeOption += check_tradeoption1.text
            if(check_tradeoption3.isChecked == true) tradeOption += check_tradeoption1.text

            if(edit_registertitle.text.toString() == "" || edit_registertitle.text.toString() == null)
            {
                Toast.makeText(this, "제목을 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
            /*else if(image_insert1.drawable==resources.getDrawable(R.drawable.default_image))
            {
                Toast.makeText(this, "이미지를 확인해주세요.", Toast.LENGTH_SHORT).show()
            }*/
            else if(text_selectcategory.text == "설정해주세요.")
            {
                Toast.makeText(this, "카테고리를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if(text_selectsubcategory.text == "설정해주세요.")
            {
                Toast.makeText(this, "하위 카테고리를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if(productType == "")
            {
                Toast.makeText(this, "상품유형을 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if(productStatus == "")
            {
                Toast.makeText(this, "상품상태를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if(edit_registerprice.text.toString() == "")
            {
                Toast.makeText(this, "가격을 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if(edit_registerinfo.text.toString() == "")
            {
                Toast.makeText(this, "상세정보를 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if(tradeOption == "")
            {
                Toast.makeText(this, "거래유형을 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
            else
            {
                VolleyService.registerProductReq("asd", edit_registertitle.text.toString(), text_selectcategory.text.toString() , text_selectsubcategory.text.toString(), productType, productStatus, edit_registerbrand.text.toString(), edit_registerprice.text.toString(), sellerStore, edit_registerinfo.text.toString(), tradeOption, edit_registeraddress.text.toString(), registerDate, 0, 0, this, { success->})
                Toast.makeText(this, "등록완료", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId)
        {
            android.R.id.home-> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
