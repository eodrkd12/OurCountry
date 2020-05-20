package com.honestyandpassion.ourcountry.Item

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
    val userNickname: String?
)