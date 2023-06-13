package com.bangkit.mocca.data.online.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryResponse(
    @SerializedName("name_category")
    val nameCategory: String,

    @SerializedName("sisabudget")
    val sisaBudget: Int
) : Parcelable