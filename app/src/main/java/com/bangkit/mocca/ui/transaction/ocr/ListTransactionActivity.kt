package com.bangkit.mocca.ui.transaction.ocr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.mocca.R
import com.bangkit.mocca.databinding.ActivityListTransactionBinding

class ListTransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}