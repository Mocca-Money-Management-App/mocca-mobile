package com.bangkit.mocca.ui.transaction.ocr

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.bangkit.mocca.data.Result
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.mocca.MainActivity
import com.bangkit.mocca.R
import com.bangkit.mocca.data.remote.response.ocr.OcrListResponse
import com.bangkit.mocca.data.remote.response.ocr.OcrResponse
import com.bangkit.mocca.databinding.ActivityListTransactionBinding
import com.bangkit.mocca.utils.SwipeToDeleteCallback
import com.google.android.material.snackbar.Snackbar
import okhttp3.internal.notify

class ListTransactionOcrActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListTransactionBinding

    private val ocrViewModel by viewModels<OcrViewModel>()

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

            val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val mutableList = mutableListOf(productList.productList)
                    if (position in mutableList.indices) {
                        mutableList.removeAt(position)
                        binding.rvListTransaction.adapter?.notifyItemRemoved(position)
                    }
                }
            }

            val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
            itemTouchHelper.attachToRecyclerView(binding.rvListTransaction)
        }

        binding.btnSendTransaction.setOnClickListener {
            if (productList != null) {
                val updatedList = productList.productList.map { product ->
                    product.copy(categoryId = 1)
                }
                ocrViewModel.postListTransaction(1, updatedList)
                ocrViewModel.insertTransactionResult.observe(this) { result ->
                    when(result) {
                        is Result.Loading -> ""

                        is Result.Success -> {
                            val intent = Intent(this@ListTransactionOcrActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                        is Result.Error -> ""
                    }
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setProductData(listProduct: List<OcrResponse>) {
        val adapter = ListProductOcrAdapter(this@ListTransactionOcrActivity, listProduct)
        binding.rvListTransaction.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    companion object {
        const val EXTRA_PRODUCT_LIST = "extra_product_list"
    }
}