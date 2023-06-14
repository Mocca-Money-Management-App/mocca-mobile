package com.bangkit.mocca.data.remote.response

import com.google.gson.annotations.SerializedName

data class OcrListResponse(

    @field:SerializedName("success")
    var success: Int? = null,

    @field:SerializedName("product")
    val productList: List<OcrResponse>
)
