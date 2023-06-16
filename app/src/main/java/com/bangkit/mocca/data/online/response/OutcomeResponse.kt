package com.bangkit.mocca.data.online.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class OutcomeResponse (
    @SerializedName("totalOutcome")
    val totalOutcome: List<OutcomeItem>
) : Parcelable

@Parcelize
data class OutcomeItem(
    @SerializedName("TotalExpense")
    val totalExpense: Int
) : Parcelable