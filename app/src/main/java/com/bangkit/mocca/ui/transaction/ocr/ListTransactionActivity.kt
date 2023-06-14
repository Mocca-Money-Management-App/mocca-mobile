package com.bangkit.mocca.ui.transaction.ocr

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bangkit.mocca.R
import com.bangkit.mocca.data.remote.response.OcrListResponse
import com.bangkit.mocca.data.remote.response.OcrResponse
import com.bangkit.mocca.databinding.ActivityListTransactionBinding

class ListTransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListTransactionBinding

    var productList: OcrListResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productList = intent.getParcelableExtra(EXTRA_PRODUCT_LIST)
        Log.e("List Transaction", "$productList")
    }

    companion object {
        private const val EXTRA_PRODUCT_LIST = "extra_product_list"

        fun intent(context: Context, productList: OcrListResponse) {
            val intent = Intent(context, ListTransactionActivity::class.java)
            intent.putExtra(EXTRA_PRODUCT_LIST, productList)
            context.startActivity(intent)
        }
    }
}