package com.bangkit.mocca.data.remote.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class OcrListResponse(

    @field:SerializedName("success")
    var success: Int? = null,

    @field:SerializedName("product")
    val productList: List<OcrResponse>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.createTypedArrayList(OcrResponse.CREATOR) ?: emptyList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(success)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OcrListResponse> {
        override fun createFromParcel(parcel: Parcel): OcrListResponse {
            return OcrListResponse(parcel)
        }

        override fun newArray(size: Int): Array<OcrListResponse?> {
            return arrayOfNulls(size)
        }
    }
}
