package com.bangkit.mocca.utils

import com.bangkit.mocca.data.model.TransactionModel

object DataDummy {

    fun generateDataTransactionDummy(): List<TransactionModel> {
        val listTransaction = ArrayList<TransactionModel>()

        listTransaction.add(
            TransactionModel(
                id = 1,
                description = "",
                date  = "",
                price = 0.toDouble(),
                category = "Makanan",
                type = "Income"
            )
        )

        listTransaction.add(
            TransactionModel(
                id = 2,
                description = "",
                date  = "",
                price = 0.toDouble(),
                category = "Makanan",
                type = "Income"
            )
        )

        listTransaction.add(
            TransactionModel(
                id = 3,
                description = "",
                date  = "",
                price = 0.toDouble(),
                category = "Makanan",
                type = "Income"
            )
        )

        listTransaction.add(
            TransactionModel(
                id = 4,
                description = "",
                date  = "",
                price = 0.toDouble(),
                category = "Makanan",
                type = "Income"
            )
        )

        listTransaction.add(
            TransactionModel(
                id = 5,
                description = "",
                date  = "",
                price = 0.toDouble(),
                category = "Makanan",
                type = "Income"
            )
        )

        listTransaction.add(
            TransactionModel(
                id = 6,
                description = "",
                date  = "",
                price = 0.toDouble(),
                category = "Makanan",
                type = "Income"
            )
        )

        listTransaction.add(
            TransactionModel(
                id = 7,
                description = "",
                date  = "",
                price = 0.toDouble(),
                category = "Makanan",
                type = "Income"
            )
        )

        listTransaction.add(
            TransactionModel(
                id = 8,
                description = "",
                date  = "",
                price = 0.toDouble(),
                category = "Makanan",
                type = "Income"
            )
        )

        listTransaction.add(
            TransactionModel(
                id = 9,
                description = "",
                date  = "",
                price = 0.toDouble(),
                category = "Makanan",
                type = "Income"
            )
        )

        listTransaction.add(
            TransactionModel(
                id = 10,
                description = "",
                date  = "",
                price = 0.toDouble(),
                category = "Makanan",
                type = "Income"
            )
        )

        return listTransaction
    }
}