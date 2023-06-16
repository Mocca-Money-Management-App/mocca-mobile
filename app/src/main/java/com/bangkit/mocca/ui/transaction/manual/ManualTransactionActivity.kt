package com.bangkit.mocca.ui.transaction.manual

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import com.bangkit.mocca.MainActivity
import com.bangkit.mocca.MainActivity.Companion.USER
import com.bangkit.mocca.R
import com.bangkit.mocca.data.model.UserModel
import com.bangkit.mocca.data.model.UserPreference
import com.bangkit.mocca.databinding.ActivityManualTransactionBinding
import com.bangkit.mocca.ui.budget.BudgetFragment
import com.bangkit.mocca.ui.home.HomeFragment
import com.bangkit.mocca.utils.ViewModelFactory
import com.bangkit.mocca.utils.dataStore

class ManualTransactionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityManualTransactionBinding
    private lateinit var viewModel: ManualTransactionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManualTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupViewModel()
        setupAction()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this, ViewModelFactory(UserPreference.getInstance(this@ManualTransactionActivity.dataStore))
        )[ManualTransactionViewModel::class.java]
    }

    private fun setupAction() {
        var type = 0
        var category = 0

        val spinnerType: Spinner = binding.spinnerType
        ArrayAdapter.createFromResource(
            this,
            R.array.type_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerType.adapter = adapter
        }
        spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                if (position == 1) {
                    binding.spinnerCategory.visibility = View.VISIBLE
                    binding.icSpinner.visibility = View.VISIBLE
                } else {
                    binding.spinnerCategory.visibility = View.GONE
                    binding.icSpinner.visibility = View.GONE
                }
                type = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val spinnerCategory: Spinner = binding.spinnerCategory
        ArrayAdapter.createFromResource(
            this,
            R.array.category_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerCategory.adapter = adapter
        }
        spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                category = position + 1
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.apply {
            edAddNumber.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(c: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(c: CharSequence?, start: Int, before: Int, count: Int) {
                    enableButton()
                }

                override fun afterTextChanged(s: Editable?) {
                    edAddNumber.error = if (edAddNumber.text.toString().isEmpty()) {
                        getString(R.string.no_empty)
                    } else null
                }
            })

            edProductName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(c: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(c: CharSequence?, start: Int, before: Int, count: Int) {
                    enableButton()
                }

                override fun afterTextChanged(s: Editable?) {
                    edProductName.error = if (edProductName.text.toString().isEmpty()) {
                        getString(R.string.no_empty)
                    } else null
                }
            })

            btnSave.setOnClickListener {
                val price = edAddNumber.text.toString().toFloat()
                val product = edProductName.text.toString()
                viewModel.apply {
                    isLoading.observe(this@ManualTransactionActivity) {
                        showLoading(it)
                    }

                    getUser().observe(this@ManualTransactionActivity) { result ->
                        if (type == 0) {
                            viewModel.addIncome(result.idUser, product, price)
                        } else if (type == 1) {
                            viewModel.addOutcome(result.idUser, product, category, price)
                        }
                        success.observe(this@ManualTransactionActivity) {
                            if (it == "Insert Successful") {
                                successAddManual()
                            } else {
                                invalidAddManual(it)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun successAddManual() {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.congrats))
            setMessage(getString(R.string.add_manual_success))
            setPositiveButton(getString(R.string.ok)) { _, _ ->
                val intent = Intent(this@ManualTransactionActivity, MainActivity::class.java)
                startActivity(intent,
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this@ManualTransactionActivity).toBundle())
                finish()
            }
            create()
            show()
        }
    }

    private fun invalidAddManual(message: String?) {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.invalid_add_manual))
            setMessage(message)
            setPositiveButton(getString(R.string.ok)) { ok, _ ->
                ok.cancel()
            }
            create()
            show()
        }
    }

    private fun enableButton() {
        val price = binding.edAddNumber.text
        val product = binding.edProductName.text.toString()

        (!price.isNullOrEmpty() && !product.isNullOrEmpty()).also {
            binding.btnSave.isEnabled = it }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}