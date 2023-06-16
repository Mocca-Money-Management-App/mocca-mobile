package com.bangkit.mocca.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val idUser: Int,
    val name: String,
    val email: String
) : Parcelable