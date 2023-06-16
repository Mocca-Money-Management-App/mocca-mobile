package com.bangkit.mocca.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import com.bangkit.mocca.MainActivity
import com.bangkit.mocca.R
import com.bangkit.mocca.data.model.UserPreference
import com.bangkit.mocca.databinding.ActivityLoginBinding
import com.bangkit.mocca.ui.register.RegisterActivity
import com.bangkit.mocca.utils.ViewModelFactory
import com.bangkit.mocca.utils.dataStore
import com.bangkit.mocca.utils.isValidEmail
import com.bangkit.mocca.utils.isValidPassword

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        enableButton()
        setupViewModel()
        setupAction()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[LoginViewModel::class.java]
    }

    private fun setupAction() {
        binding.apply {
            edEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                    // Do nothing.
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (edEmail.text.toString().isEmpty()){
                        edEmail.error = getString(R.string.no_empty)
                    } else if (!s.isValidEmail()) {
                        edEmail.error = getString(R.string.wrong_format)
                    }
                }

                override fun afterTextChanged(s: Editable) {
                    enableButton()
                }
            })

            edPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                    // Do nothing.
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    val password = s.toString()
                    if (password.isBlank()){
                        edPassword.error = getString(R.string.no_empty)
                    } else if (password.length < 8) {
                        edPassword.error = getString(R.string.more_than_8)
                    }
                }

                override fun afterTextChanged(s: Editable) {
                    enableButton()
                }
            })

            btnLogin.setOnClickListener {
                val email = binding.edEmail.text.toString()
                val password = binding.edPassword.text.toString()
                viewModel.apply {
                    isLoading.observe(this@LoginActivity) {
                        showLoading(it)
                    }

                    login(email, password)
                    success.observe(this@LoginActivity) {
                        when (it) {
                            "Login Success" -> {
                                successLogin()
                            }
                            "Incorrect Username and/or Password!" -> {
                                invalidAccount()
                            }
                            else -> {
                                invalidLogin(it)
                            }
                        }
                    }
                }
            }

            tvRegister.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun enableButton() {
        val email = binding.edEmail.text.toString()
        val password = binding.edPassword.text.toString()

        (email.isNotEmpty() && email.isValidEmail()
                && password.isNotEmpty() && password.isValidPassword()).also {
            binding.btnLogin.isEnabled = it }
    }

    private fun successLogin() {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.congrats))
            setMessage(getString(R.string.login_success))
            setPositiveButton(getString(R.string.next)) { _, _ ->
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent,
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this@LoginActivity).toBundle())
                finish()
            }
            create()
            show()
        }
    }

    private fun invalidAccount() {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.invalid_login))
            setMessage(getString(R.string.invalid_account))
            setPositiveButton(getString(R.string.ok)) { ok, _ ->
                ok.cancel()
            }
            create()
            show()
        }
    }

    private fun invalidLogin(message: String?) {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.invalid_login))
            setMessage(message)
            setPositiveButton(getString(R.string.ok)) { ok, _ ->
                ok.cancel()
            }
            create()
            show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}