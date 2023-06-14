package com.bangkit.mocca.data.remote.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class OcrResponse(

    @field:SerializedName("nama_produk")
    var name_product: String? = null,

    @field:SerializedName("harga")
    var price: Int? = null,

    @field:SerializedName("category")
    var category: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name_product)
        parcel.writeValue(price)
        parcel.writeString(category)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OcrResponse> {
        override fun createFromParcel(parcel: Parcel): OcrResponse {
            return OcrResponse(parcel)
        }

        override fun newArray(size: Int): Array<OcrResponse?> {
            return arrayOfNulls(size)
        }
    }
}
