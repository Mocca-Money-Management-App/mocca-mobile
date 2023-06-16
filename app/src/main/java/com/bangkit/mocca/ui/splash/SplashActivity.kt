package com.bangkit.mocca.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.bangkit.mocca.MainActivity
import com.bangkit.mocca.R
import com.bangkit.mocca.data.model.UserPreference
import com.bangkit.mocca.databinding.ActivitySplashBinding
import com.bangkit.mocca.ui.login.LoginActivity
import com.bangkit.mocca.utils.ViewModelFactory
import com.bangkit.mocca.utils.dataStore

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var viewModel: SplashViewModel
    private var isLogin = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupViewModel()

        val handler = Handler()
        handler.postDelayed({
            val intent = if (isLogin) {
                Intent(this@SplashActivity, MainActivity::class.java)
            } else {
                Intent(this@SplashActivity, LoginActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, splash_timer)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this, ViewModelFactory(UserPreference.getInstance(dataStore))
        )[SplashViewModel::class.java]

        viewModel.apply {
            getUser().observe(this@SplashActivity) {
                if (it.idUser != 0) {
                    isLogin = true
                }
            }
        }
    }

    companion object {
        const val splash_timer = 2000L
    }
}