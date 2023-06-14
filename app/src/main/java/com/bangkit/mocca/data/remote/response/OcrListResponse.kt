package com.bangkit.mocca.data.remote.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class OcrListResponse(

    @field:SerializedName("success")
    var success: Int? = null,

    @field:SerializedName("product")
    val productList: List<OcrResponse>
) : Parcelable