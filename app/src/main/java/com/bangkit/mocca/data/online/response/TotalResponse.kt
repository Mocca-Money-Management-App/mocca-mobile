package com.bangkit.mocca.data.online.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TotalResponse (
    @SerializedName("SUM(PRICE)")
    val sum: Int
): Parcelable