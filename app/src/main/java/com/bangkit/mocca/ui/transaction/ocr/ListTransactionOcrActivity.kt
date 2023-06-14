package com.bangkit.mocca.ui.transaction.ocr

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.mocca.data.remote.response.OcrListResponse
import com.bangkit.mocca.data.remote.response.OcrResponse
import com.bangkit.mocca.databinding.ActivityListTransactionBinding

class ListTransactionOcrActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListTransactionBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Scan Result"

        val productList = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_PRODUCT_LIST, OcrListResponse::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_PRODUCT_LIST)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvListTransaction.layoutManager = layoutManager
        binding.rvListTransaction.setHasFixedSize(true)

        if (productList != null) {
            setProductData(productList.productList)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setProductData(listProduct: List<OcrResponse>) {
        val adapter = ListProductOcrAdapter(listProduct)
        binding.rvListTransaction.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    companion object {
        const val EXTRA_PRODUCT_LIST = "extra_product_list"
    }
}