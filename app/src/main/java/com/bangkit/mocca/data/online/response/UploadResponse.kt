package com.bangkit.mocca.data.online.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UploadResponse(
    @SerializedName("success")
    val success: Int,

    @SerializedName("product")
    val listStory: List<ListUploadItem>
) : Parcelable

@Parcelize
data class ListUploadItem(
    @SerializedName("name_product")
    val name_product: String,

    @SerializedName("price")
    val price: Float,

    @SerializedName("category")
    val category: String
) : Parcelable