package com.bangkit.mocca.data.remote.response

import com.google.gson.annotations.SerializedName

data class OcrResponse(

    @field:SerializedName("nama_produk")
    var name_product: String? = null,

    @field:SerializedName("harga")
    var price: Int? = null,

    @field:SerializedName("category")
    var category: String? = null
)
