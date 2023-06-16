package com.bangkit.mocca.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import com.bangkit.mocca.R
import com.bangkit.mocca.data.model.UserPreference
import com.bangkit.mocca.databinding.ActivityRegisterBinding
import com.bangkit.mocca.ui.login.LoginActivity
import com.bangkit.mocca.ui.login.LoginViewModel
import com.bangkit.mocca.utils.ViewModelFactory
import com.bangkit.mocca.utils.dataStore
import com.bangkit.mocca.utils.isValidEmail
import com.bangkit.mocca.utils.isValidPassword

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
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
        )[RegisterViewModel::class.java]

        viewModel.isLoading.observe(this@RegisterActivity) {
            showLoading(it)
        }
    }

    private fun setupAction() {
        binding.apply {
            edName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                    // Do nothing.
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (edName.text.toString().isEmpty()){
                        edName.error = getString(R.string.no_empty)
                    }
                }

                override fun afterTextChanged(s: Editable) {
                    enableButton()
                }
            })

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

            edPhoneNumber.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                    // Do nothing.
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (edPhoneNumber.text.toString().isEmpty()){
                        edPhoneNumber.error = getString(R.string.no_empty)
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

            btnRegister.setOnClickListener {
                val name = binding.edName.text.toString()
                val email = binding.edEmail.text.toString()
                val phone = binding.edPhoneNumber.text.toString()
                val password = binding.edPassword.text.toString()
                viewModel.apply {
                    register(name, email, phone, password)
                    success.observe(this@RegisterActivity) {
                        when (it) {
                            "Register successful" -> {
                                successRegister()
                            }
                            else -> {
                                invalidRegister(it)
                            }
                        }
                    }
                }
            }

            tvLogin.setOnClickListener {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun successRegister() {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.congrats))
            setMessage(getString(R.string.register_success))
            setPositiveButton(getString(R.string.login)) { _, _ ->
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent,
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this@RegisterActivity).toBundle())
                finish()
            }
            create()
            show()
        }
    }

    private fun invalidRegister(message: String?) {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.invalid_register))
            setMessage(message)
            setPositiveButton(getString(R.string.ok)) { ok, _ ->
                ok.cancel()
            }
            create()
            show()
        }
    }

    private fun enableButton() {
        val name = binding.edName.text.toString()
        val email = binding.edEmail.text
        val phone = binding.edPhoneNumber.text.toString()
        val password = binding.edPassword.text.toString()
        (name.isNotEmpty() && phone.isNotEmpty() && email != null && email.toString().isNotEmpty() && email.isValidEmail()
                && password.isNotEmpty() && password.isValidPassword()).also {
            binding.btnRegister.isEnabled = it }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}