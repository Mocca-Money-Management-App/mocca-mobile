package com.bangkit.mocca.data.model

data class TransactionModel (
    val id: Int,
    val description: String,
    val date: String,
    val price: Double,
    val category: String,
    val type: String
)