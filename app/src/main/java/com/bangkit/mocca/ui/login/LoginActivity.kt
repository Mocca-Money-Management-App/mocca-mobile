package com.bangkit.mocca.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.mocca.R
import com.bangkit.mocca.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    private fun setupViewModel() {
        viewModel.apply {  }
    }

    private fun setupAction() {
        binding.apply {  }
    }
}