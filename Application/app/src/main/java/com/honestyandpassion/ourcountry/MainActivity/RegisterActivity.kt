package com.honestyandpassion.ourcountry.MainActivity

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.honestyandpassion.ourcountry.Class.ToolbarSetting
import com.honestyandpassion.ourcountry.Class.UserInfo
import com.honestyandpassion.ourcountry.Fragment.HomeFragment
import com.honestyandpassion.ourcountry.IntroActivity.SelectCategoryActivity
import com.honestyandpassion.ourcountry.Item.Product
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.R
import kotlinx.android.synthetic.main.activity_register.*
import java.io.FileNotFoundException
import java.io.IOException
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

class RegisterActivity : ToolbarSetting() {

    val PICK_FROM_CAMERA = 0
    val PICK_FROM_ALBUM = 1
    val CROP_FROM_CAMERA = 2
    val CROP_FROM_ALBUM = 3

    var imageCaptureUri: Uri? = null

    var productType: String = ""
    var productStatus: String = ""
    var tradeOption: String = ""
    var sellerStore: Int = 0

    var imageArray = ArrayList<ImageView>()
    var insertArray = ArrayList<ImageView>()
    var imageView: ImageView? = null

    companion object {
        var categoryText: TextView? = null
        var subCategoryText: TextView? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        categoryText = findViewById(R.id.text_selectcategory)
        subCategoryText = findViewById(R.id.text_selectsubcategory)

        var intent = intent
        if(intent.getStringExtra("registerType") == "수정") {
            var product=intent.getParcelableExtra<Product>("product")
            edit_registertitle.setText(product.registerTitle)
            btn_registercomplete.setText("수정완료")
            categoryText!!.text = product.productCategory
            subCategoryText!!.text = product.productSubCategory
            productType = product.productType!!
            productStatus = product.productStatus!!
            sellerStore = product.sellerStore!!
            tradeOption = product.tradeOption!!
            var registerId : Int = product.registerId!!

            when(productType) {
                "산지직송" -> radio_typeoption1.setChecked(true)
                "팝니다" -> radio_typeoption2.setChecked(true)
                "삽니다" -> radio_typeoption3.setChecked(true)
                "무료나눔" -> radio_typeoption4.setChecked(true)
                "교환신청" -> radio_typeoption5.setChecked(true)
            }

            when(productStatus) {
                "신선식품" -> radio_statusoption1.setChecked(true)
                "가공식품" -> radio_statusoption2.setChecked(true)
                "새상품" -> radio_statusoption3.setChecked(true)
                "중고" -> radio_statusoption4.setChecked(true)
            }

            edit_registerbrand.setText(product.productBrand)
            edit_registerprice.setText(product.productPrice)

            when(sellerStore) {
                0 -> check_store.setChecked(false)
                1 -> check_store.setChecked(true)
            }

            edit_registerinfo.setText(product.registerContent)

            if(tradeOption.contains("택배")) check_tradeoption1.setChecked(true)
            if(tradeOption.contains("무료배송")) check_tradeoption2.setChecked(true)
            if(tradeOption.contains("직거래")) check_tradeoption3.setChecked(true)

            edit_registeraddress.setText(product.sellerAddress)

            btn_registercomplete.setOnClickListener{
                tradeOption = ""
                if (check_tradeoption1.isChecked == true) tradeOption += check_tradeoption1.text
                if (check_tradeoption2.isChecked == true) tradeOption += check_tradeoption2.text
                if (check_tradeoption3.isChecked == true) tradeOption += check_tradeoption3.text

                if(insertArray.size==0){
                    Toast.makeText(this, "상품 이미지를 등록해주세요.",Toast.LENGTH_SHORT).show()
                }
                else if (edit_registertitle.text.toString() == "" || edit_registertitle.text.toString() == null) {
                    Toast.makeText(this, "제목을 확인해주세요.", Toast.LENGTH_SHORT).show()
                }
                else if (text_selectcategory.text == "설정해주세요.") {
                    Toast.makeText(this, "카테고리를 선택해주세요.", Toast.LENGTH_SHORT).show()
                } else if (text_selectsubcategory.text == "설정해주세요.") {
                    Toast.makeText(this, "하위 카테고리를 선택해주세요.", Toast.LENGTH_SHORT).show()
                } else if (productType == "") {
                    Toast.makeText(this, "상품유형을 선택해주세요.", Toast.LENGTH_SHORT).show()
                } else if (productStatus == "") {
                    Toast.makeText(this, "상품상태를 선택해주세요.", Toast.LENGTH_SHORT).show()
                } else if (edit_registerprice.text.toString() == "") {
                    Toast.makeText(this, "가격을 확인해주세요.", Toast.LENGTH_SHORT).show()
                } else if (edit_registerinfo.text.toString() == "") {
                    Toast.makeText(this, "상세정보를 확인해주세요.", Toast.LENGTH_SHORT).show()
                } else if (tradeOption == "") {
                    Toast.makeText(this, "거래유형을 선택해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
                    val registerDate =
                        current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    VolleyService.updateProductReq(
                        UserInfo.ID,
                        edit_registertitle.text.toString(),
                        text_selectcategory.text.toString(),
                        text_selectsubcategory.text.toString(),
                        productType,
                        productStatus,
                        edit_registerbrand.text.toString(),
                        edit_registerprice.text.toString(),
                        sellerStore,
                        edit_registerinfo.text.toString(),
                        tradeOption,
                        edit_registeraddress.text.toString(),
                        registerDate,
                        this,
                        { success ->
                            if(success== "success") {
                                Toast.makeText(this, "수정완료", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                            /*for (i in 0..insertArray.size - 1) {
                                var bitmap =
                                    ((imageArray[i].drawable as Drawable) as BitmapDrawable).bitmap
                                VolleyService.insertImageReq(registerId, edit_registertitle.text.toString(), bitmap, this,{success ->
                                    var handler=HomeFragment.HANDLER
                                    var msg=handler!!.obtainMessage()
                                    msg.what=0
                                    handler.sendMessage(msg)
                                })
                            }*/
                        })
                }
            }
        }
        else {
            btn_registercomplete.setOnClickListener {
                if (check_tradeoption1.isChecked == true) tradeOption += check_tradeoption1.text
                if (check_tradeoption2.isChecked == true) tradeOption += check_tradeoption1.text
                if (check_tradeoption3.isChecked == true) tradeOption += check_tradeoption1.text

                if(insertArray.size==0){
                    Toast.makeText(this, "상품 이미지를 등록해주세요.",Toast.LENGTH_SHORT).show()
                }
                else if (edit_registertitle.text.toString() == "" || edit_registertitle.text.toString() == null) {
                    Toast.makeText(this, "제목을 확인해주세요.", Toast.LENGTH_SHORT).show()
                }
                else if (text_selectcategory.text == "설정해주세요.") {
                    Toast.makeText(this, "카테고리를 선택해주세요.", Toast.LENGTH_SHORT).show()
                } else if (text_selectsubcategory.text == "설정해주세요.") {
                    Toast.makeText(this, "하위 카테고리를 선택해주세요.", Toast.LENGTH_SHORT).show()
                } else if (productType == "") {
                    Toast.makeText(this, "상품유형을 선택해주세요.", Toast.LENGTH_SHORT).show()
                } else if (productStatus == "") {
                    Toast.makeText(this, "상품상태를 선택해주세요.", Toast.LENGTH_SHORT).show()
                } else if (edit_registerprice.text.toString() == "") {
                    Toast.makeText(this, "가격을 확인해주세요.", Toast.LENGTH_SHORT).show()
                } else if (edit_registerinfo.text.toString() == "") {
                    Toast.makeText(this, "상세정보를 확인해주세요.", Toast.LENGTH_SHORT).show()
                } else if (tradeOption == "") {
                    Toast.makeText(this, "거래유형을 선택해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
                    val registerDate =
                        current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    VolleyService.registerProductReq(
                        UserInfo.ID,
                        edit_registertitle.text.toString(),
                        text_selectcategory.text.toString(),
                        text_selectsubcategory.text.toString(),
                        productType,
                        productStatus,
                        edit_registerbrand.text.toString(),
                        edit_registerprice.text.toString(),
                        sellerStore,
                        edit_registerinfo.text.toString(),
                        tradeOption,
                        edit_registeraddress.text.toString(),
                        registerDate,
                        0,
                        0,
                        UserInfo.NICKNAME,
                        this,
                        { success ->
                            var jsonObject = success
                            var registerId = jsonObject!!.getInt("register_id")
                            var registerTitle = jsonObject!!.getString("register_title")
                            for (i in 0..insertArray.size - 1) {
                                var bitmap =
                                    ((imageArray[i].drawable as Drawable) as BitmapDrawable).bitmap
                                VolleyService.insertImageReq(registerId, registerTitle, bitmap, this,{success ->
                                    var handler=HomeFragment.HANDLER
                                    var msg=handler!!.obtainMessage()
                                    msg.what=0
                                    handler.sendMessage(msg)
                                })
                            }
                        })
                    Toast.makeText(this, "등록완료", Toast.LENGTH_SHORT).show()

                    finish()
                }
            }
        }

        var toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_register)
        toolbarBinding(toolbar, "상품등록")

        layout_selectcategory.setOnClickListener {
            var intent = Intent(this, SelectCategoryActivity::class.java)
            startActivity(intent)
        }

        radiogroup_producttype.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.radio_typeoption1 -> productType = "산지직송"
                R.id.radio_typeoption2 -> productType = "팝니다"
                R.id.radio_typeoption3 -> productType = "삽니다"
                R.id.radio_typeoption4 -> productType = "무료나눔"
                R.id.radio_typeoption5 -> productType = "교환신청"
            }
        }

        /*radiogroup_tradeoption.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.check_tradeoption1 -> tradeOption = "택배"
                R.id.check_tradeoption2 -> tradeOption = "무료배송"
                R.id.check_tradeoption3 -> tradeOption = "직거래"
            }
        }*/

        radiogroup_productstatus.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.radio_statusoption1 -> productStatus = "신선식품"
                R.id.radio_statusoption2 -> productStatus = "가공식품"
                R.id.radio_statusoption3 -> productStatus = "새상품"
                R.id.radio_statusoption4 -> productStatus = "중고"
            }
        }


        check_store.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                sellerStore = 1
            } else {
                sellerStore = 0
            }
        }



        imageArray.add(image_insert1)
        imageArray.add(image_insert2)
        imageArray.add(image_insert3)
        imageArray.add(image_insert4)
        imageArray.add(image_insert5)
        imageArray.add(image_insert6)
        imageArray.add(image_insert7)
        imageArray.add(image_insert8)
        imageArray.add(image_insert9)
        imageArray.add(image_insert10)

        for (i in 0..imageArray.size - 1) {
            imageArray[i].setOnClickListener {
                imageView = it as ImageView
                insertArray.add(imageView!!)
                var imageChangeDialog = layoutInflater.inflate(R.layout.dialog_imageupload, null)
                var dialog = Dialog(this)
                dialog.setContentView(imageChangeDialog)

                var btnCancel = imageChangeDialog.findViewById<Button>(R.id.btn_imagechangecancel)
                var btnAlbum =
                    imageChangeDialog.findViewById<Button>(R.id.btn_changeimage_from_phone)
                var btnCam = imageChangeDialog.findViewById<Button>(R.id.btn_changeimage_from_cam)

                btnAlbum.setOnClickListener({
                    //이미지변경버튼
                    var albumIntent = Intent(Intent.ACTION_PICK)
                    albumIntent.setType(MediaStore.Images.Media.CONTENT_TYPE)
                    startActivityForResult(albumIntent, PICK_FROM_ALBUM)
                    dialog.dismiss()
                })
                btnCam.setOnClickListener({
                    var cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(cameraIntent, PICK_FROM_CAMERA)
                    dialog.dismiss()
                })
                btnCancel.setOnClickListener({
                    //취소버튼
                    dialog.dismiss()
                })
                dialog!!.show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }

        when (requestCode) {
            PICK_FROM_CAMERA -> {
                val imageBitmap = data!!.extras.get("data") as Bitmap
                imageView!!.setImageBitmap(imageBitmap)
            }
            PICK_FROM_ALBUM -> {
                imageCaptureUri = data!!.data

                try {
                    val imageBitmap =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, imageCaptureUri)
                    imageView!!.setImageBitmap(imageBitmap)
                } catch (e: FileNotFoundException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                } catch (e: IOException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }
            }
            CROP_FROM_CAMERA -> {

            }
            CROP_FROM_ALBUM -> {

            }
        }
    }
}
