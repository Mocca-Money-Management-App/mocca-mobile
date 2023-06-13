package com.bangkit.mocca.data.online.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionResponse (
    @SerializedName("idUser")
    val idUser: Int,

    @SerializedName("name_category")
    val nameCategory: String,

    @SerializedName("name_product")
    val nameProduct: String,

    @SerializedName("idUser")
    val price: Int,

    @SerializedName("created_at")
    val createdAt: String
) : Parcelable