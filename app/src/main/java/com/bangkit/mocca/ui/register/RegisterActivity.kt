package com.bangkit.mocca.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.mocca.R
import com.bangkit.mocca.databinding.ActivityRegisterBinding
import com.bangkit.mocca.ui.login.LoginViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupViewModel() {

    }

    private fun setupAction() {

    }
}