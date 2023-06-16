package com.bangkit.mocca.data.online.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionResponse (
    @SerializedName("transaction")
    val transaction: List<ListTransactionItem>
) : Parcelable

@Parcelize
data class ListTransactionItem(
    @SerializedName("idUser")
    val idUser: Int,

    @SerializedName("name_category")
    val nameCategory: String,

    @SerializedName("name_product")
    val nameProduct: String,

    @SerializedName("price")
    val price: Int,

    @SerializedName("created_at")
    val createdAt: String
): Parcelable