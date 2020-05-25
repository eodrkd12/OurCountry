package com.honestyandpassion.ourcountry.Item

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Product(
    val registerId: Int?,
    val userId: String?,
    val registerTitle: String?,
    val productCategory: String?,
    val productSubCategory: String?,
    val productType: String?,
    val productStatus: String?,
    val productBrand: String?,
    val productPrice: String?,
    val sellerStore: Int?,
    val registerContent: String?,
    val tradeOption: String?,
    val sellerAddress: String?,
    val registerDate: String?,
    val registerLike: Int?,
    val registerView: Int?,
    val userNickname: String?,
    val imageList: ArrayList<Bitmap>?
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readArrayList(ArrayList::class.java.classLoader) as? ArrayList<Bitmap>
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(registerId)
        parcel.writeString(userId)
        parcel.writeString(registerTitle)
        parcel.writeString(productCategory)
        parcel.writeString(productSubCategory)
        parcel.writeString(productType)
        parcel.writeString(productStatus)
        parcel.writeString(productBrand)
        parcel.writeString(productPrice)
        parcel.writeValue(sellerStore)
        parcel.writeString(registerContent)
        parcel.writeString(tradeOption)
        parcel.writeString(sellerAddress)
        parcel.writeString(registerDate)
        parcel.writeValue(registerLike)
        parcel.writeValue(registerView)
        parcel.writeString(userNickname)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }

}
