package com.bangkit.mocca.data.remote.response.ocr

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class OcrResponse(

    @field:SerializedName("name_product")
    var name_product: String? = null,

    @field:SerializedName("price")
    var price: Int? = null,

    @field:SerializedName("categoryId")
    var categoryId: Int? = null
) : Parcelable