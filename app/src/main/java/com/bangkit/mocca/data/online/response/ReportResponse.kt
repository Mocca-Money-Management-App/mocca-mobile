package com.bangkit.mocca.data.online.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReportResponse(
    @SerializedName("idUser")
    val idUser: Int,

    @SerializedName("TotalRevenue")
    val totalRevenue: Int,

    @SerializedName("TotalExpense")
    val totalExpense: Int,

    @SerializedName("selisih")
    val selisih: Int
) : Parcelable