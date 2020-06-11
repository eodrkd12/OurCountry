package com.honestyandpassion.ourcountry.MainActivity

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.honestyandpassion.ourcountry.Adapter.BankAdapter
import com.honestyandpassion.ourcountry.Class.UserInfo
import com.honestyandpassion.ourcountry.IntroActivity.SelectUserTypeActivity
import com.honestyandpassion.ourcountry.Object.VolleyService
import com.honestyandpassion.ourcountry.R
import com.kakao.usermgmt.response.model.User
import kotlinx.android.synthetic.main.activity_profile_edit.*
import kr.co.bootpay.Bootpay.finish
import java.io.FileNotFoundException
import java.io.IOException
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class EditProfileActivity:AppCompatActivity() {

    val PICK_FROM_CAMERA = 0
    val PICK_FROM_ALBUM = 1
    val CROP_FROM_CAMERA = 2
    val CROP_FROM_ALBUM = 3

    var imageCaptureUri: Uri? = null

    var dialog: Dialog? = null
    var dialog_imageView: View? = null
    var dialogBtnCancel: Button? = null
    var dialogimagechange_phone: Button? = null
    var dialogimagechange_cam: Button? = null
    var dialogGrid:GridView?=null
    var dialog_Grid:View?=null

    companion object {
        var userTypeText: TextView? = null
        var beforeTypeText:String?=null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_profile_edit)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("프로필 편집")

        userTypeText = findViewById(R.id.text_selectusertype)
        userTypeText!!.setText(UserInfo.USERTYPE)
        beforeTypeText=UserInfo.USERTYPE
        edit_nickname.setText(UserInfo.NICKNAME)
        edit_email.setText(UserInfo.ID)
        edit_phone.setText(UserInfo.PHONE)
        edit_address.setText(UserInfo.ADDRESS)
        edit_about.setText(UserInfo.ABOUT)
        edit_pay2.setText(UserInfo.BANK)
        edit_pay.setText(UserInfo.ACCOUNT)
        edit_city.setText(UserInfo.ADDRESS.split(" ")[0])

        //이미지 보여줌
//        val imageByte= Base64.decode(UserInfo.IMAGE,0)
//        Toast.makeText(this, "${UserInfo.IMAGE}",Toast.LENGTH_SHORT).show()
//        val image = BitmapFactory.decodeByteArray(imageByte,0,imageByte.size)
//        img_profile.setImageBitmap(image)

//        img_edit.setOnClickListener{
//            dialog_imageView = layoutInflater.inflate(R.layout.dialog_imagechange, null)
//            dialogBtnCancel =
//                dialog_imageView!!.findViewById<Button>(R.id.btn_imagechangecancel)
//            dialogimagechange_phone =
//                dialog_imageView!!.findViewById<Button>(R.id.btn_changeimage_from_phone)
//            dialogimagechange_cam =
//                dialog_imageView!!.findViewById<Button>(R.id.btn_changeimage_from_cam)
//
//            dialogimagechange_phone!!.setOnClickListener({
//                //이미지변경버튼
//                var albumIntent = Intent(Intent.ACTION_PICK)
//                albumIntent.setType(MediaStore.Images.Media.CONTENT_TYPE)
//                startActivityForResult(albumIntent, PICK_FROM_ALBUM)
//                dialog!!.dismiss()
//            })
//            dialogimagechange_cam!!.setOnClickListener({
//                var cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                startActivityForResult(cameraIntent, PICK_FROM_CAMERA)
//                dialog!!.dismiss()
//            })
//            dialogBtnCancel!!.setOnClickListener({
//                //취소버튼
//            })
//            dialog!!.setContentView(dialog_imageView)
//            dialog!!.show()
//        }


        edit_pay2.setOnClickListener {
            val dialog = Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar)
            val dialogView=layoutInflater.inflate(R.layout.dialog_bank,null)
            val dialogGrid=dialogView.findViewById<GridView>(R.id.grid_bank)
            var bankList=resources.getStringArray(R.array.array_bank)

            var bankAdapter= BankAdapter(this,bankList)
            dialogGrid.adapter=bankAdapter

            //dialog.getWindow().statusBarColor = Color.TRANSPARENT
            dialog.getWindow().getAttributes().windowAnimations = R.style.AnimationPopupStyle
            dialog.addContentView(dialogView, ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT))
            dialog.show()

            dialogGrid.setOnItemClickListener { parent, view, position, id ->
                var name=bankAdapter.getItem(position).toString()
                edit_pay2.setText(name)
                dialog.dismiss()
            }

        }
        layout_selectusertype.setOnClickListener {
            var intent = Intent(this, SelectUserTypeActivity::class.java)
            startActivity(intent)
        }


        btn_save.setOnClickListener {
            var id = edit_email.text.toString()
            var phone = edit_phone.text.toString()
            var nickname = edit_nickname.text.toString()
            //  var city= edit_city.text.toString()
            var about = edit_about.text.toString()
            var address = edit_address.text.toString()
            var userType = text_selectusertype.text.toString()
            val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
            var date=  current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            var bank=edit_pay2.text.toString()
            var account= edit_pay.text.toString()

            VolleyService.addUserTypeReq(
                id,
                beforeTypeText.toString(),
                userType,
                date,this
            ,{success->
                    Toast.makeText(this, "타입변경.", Toast.LENGTH_SHORT).show()
                })


            VolleyService.editUserReq(
                id,
                nickname,
                phone,
                about,
                address,
                userType,
                bank,
                account,
                this,
                { success ->

                    UserInfo.USERTYPE = userType
                    UserInfo.NICKNAME = nickname
                    UserInfo.PHONE = phone
                    UserInfo.ADDRESS = address
                    UserInfo.ABOUT = about
                    UserInfo.BANK=bank
                    UserInfo.ACCOUNT=account

                    var pref = this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                    var editor = pref.edit()
                    editor
                        .putString("NICKNAME", UserInfo.NICKNAME)
                        .putString("PHONE", UserInfo.PHONE)
                        .putString("ADDRESS", UserInfo.ADDRESS)
                        .putString("ABOUT", UserInfo.ABOUT)
                        .putString("USERTYPE", UserInfo.USERTYPE)
                        .putString("BANK",UserInfo.BANK)
                        .putString("ACCOUNT",UserInfo.ACCOUNT)
                        .apply()

                    edit_nickname.setText(UserInfo.NICKNAME)
                    edit_email.setText(UserInfo.ID)
                    edit_phone.setText(UserInfo.PHONE)
                    edit_address.setText(UserInfo.ADDRESS)
                    edit_about.setText(UserInfo.ABOUT)

                    Toast.makeText(this, "내 정보가 변경되었습니다.", Toast.LENGTH_SHORT).show()
                    finish()

                })

        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }

        Log.d("uniting", "onActivityResult")

        when (requestCode) {
            PICK_FROM_CAMERA -> {
                Log.d("uniting", "CAMERA")
                val imageBitmap = data!!.extras.get("data") as Bitmap
                img_profile.setImageBitmap(imageBitmap)
                var bitmap = ((img_profile.drawable as Drawable) as BitmapDrawable).bitmap
                VolleyService.updateImageReq(UserInfo.ID, bitmap, this)

            }
            PICK_FROM_ALBUM -> {
                Log.d("uniting", "ALBUM")
                imageCaptureUri = data!!.data

                try {
                    val imageBitmap =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, imageCaptureUri)
                    img_profile.setImageBitmap(imageBitmap)
                    var bitmap = ((img_profile.drawable as Drawable) as BitmapDrawable).bitmap
                    VolleyService.updateImageReq(UserInfo.ID, bitmap, this)

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

