package com.bangkit.mocca.data.remote.response.ocr

import com.google.gson.annotations.SerializedName

data class InsertTransactionListResponse(
    @field:SerializedName("message")
    var message: String? = null,
)