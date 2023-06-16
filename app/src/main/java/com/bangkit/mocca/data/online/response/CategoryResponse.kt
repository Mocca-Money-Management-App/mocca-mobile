package com.bangkit.mocca.data.online.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryResponse(
    @SerializedName("category")
    val listCategory: List<ListCategoryItem>
) : Parcelable

@Parcelize
data class ListCategoryItem(
    @SerializedName("name_category")
    val nameCategory: String,

    @SerializedName("budget")
    val budget: Int,

    @SerializedName("sisabudget")
    val sisaBudget: Int
) : Parcelable