package com.bangkit.mocca.data.remote.response.transaction

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EditResponse(
    @SerializedName("message")
    val message: String
) : Parcelable