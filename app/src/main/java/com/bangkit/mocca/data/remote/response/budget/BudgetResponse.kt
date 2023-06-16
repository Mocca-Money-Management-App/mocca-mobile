package com.bangkit.mocca.data.remote.response.budget

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BudgetResponse (
    @SerializedName("success")
    val success: Int,

    @SerializedName("totalBudget")
    val totalBudget: List<BudgetItem>
) : Parcelable

@Parcelize
data class BudgetItem(
    @SerializedName("TotalBudget")
    val TotalBudget: Int,

    @SerializedName("TotalExpense")
    val TotalExpense: Int
) : Parcelable