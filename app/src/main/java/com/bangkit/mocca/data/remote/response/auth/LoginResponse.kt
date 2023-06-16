package com.bangkit.mocca.data.remote.response.auth

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResponse(
    @SerializedName("success")
    val success: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: LoginData
): Parcelable

@Parcelize
data class LoginData(
    @SerializedName("idUser")
    val userId: Int,

    @SerializedName("username")
    val username: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("phoneNumber")
    val phoneNumber : String,

    @SerializedName("passwordUser")
    val passwordUser: String
): Parcelable