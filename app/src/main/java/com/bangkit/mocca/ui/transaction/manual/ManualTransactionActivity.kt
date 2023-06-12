package com.bangkit.mocca.ui.transaction.manual

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.mocca.R
import com.bangkit.mocca.databinding.ActivityManualTransactionBinding

class ManualTransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManualTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManualTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
    }
}