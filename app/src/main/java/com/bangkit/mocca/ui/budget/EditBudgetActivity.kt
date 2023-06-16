package com.bangkit.mocca.ui.budget

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import com.bangkit.mocca.MainActivity
import com.bangkit.mocca.R
import com.bangkit.mocca.data.model.UserPreference
import com.bangkit.mocca.databinding.ActivityEditBudgetBinding
import com.bangkit.mocca.ui.budget.BudgetFragment.Companion.CATEGORY
import com.bangkit.mocca.utils.ViewModelFactory
import com.bangkit.mocca.utils.dataStore
import java.util.Locale

class EditBudgetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBudgetBinding
    private lateinit var viewModel: BudgetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBudgetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupViewModel()
        setupAction()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this, ViewModelFactory(UserPreference.getInstance(this@EditBudgetActivity.dataStore))
        )[BudgetViewModel::class.java]
    }

    private fun setupAction() {
        binding.apply {
            edBudget.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(c: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(c: CharSequence?, start: Int, before: Int, count: Int) {
                    enableButton()
                }

                override fun afterTextChanged(s: Editable?) {
                    edBudget.error = if (edBudget.text.toString().isEmpty()) {
                        getString(R.string.no_empty)
                    } else null
                }
            })

            btnSave.setOnClickListener {
                val budget = binding.edBudget.text.toString().toFloat()
                viewModel.apply {
                    isLoading.observe(this@EditBudgetActivity) {
                        showLoading(it)
                    }

                    val item = intent.getStringExtra(CATEGORY)
                    var category = 0
                    when (item) {
                        getString(R.string.belanja) -> { category = 1 }
                        getString(R.string.makanan_minuman) -> { category = 2 }
                        getString(R.string.kesehatan_kecantikan) -> { category = 3 }
                        getString(R.string.pendidikan) -> { category = 4 }
                        getString(R.string.transportasi) -> { category = 5 }
                        getString(R.string.pinjaman_tagihan) -> { category = 6 }
                        getString(R.string.sosial) -> { category = 7 }
                        getString(R.string.hiburan) -> { category = 8 }
                        getString(R.string.investasi) -> { category = 9 }
                    }

                    getUser().observe(this@EditBudgetActivity) { result ->
                        addCategory(result.idUser, category, budget)
                        success.observe(this@EditBudgetActivity) {
                            if (it == "Edit successful") {
                                successEditBudget()
                            } else {
                                invalidEditBudget(it)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun successEditBudget() {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.congrats))
            setMessage(getString(R.string.add_budget_success))
            setPositiveButton(getString(R.string.ok)) { _, _ ->
                val intent = Intent(this@EditBudgetActivity, MainActivity::class.java)
                startActivity(intent,
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this@EditBudgetActivity).toBundle())
                finish()
            }
            create()
            show()
        }
    }

    private fun invalidEditBudget(message: String?) {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.invalid_edit_budget))
            setMessage(message)
            setPositiveButton(getString(R.string.ok)) { ok, _ ->
                ok.cancel()
            }
            create()
            show()
        }
    }

    private fun enableButton() {
        val budget = binding.edBudget.text.toString()

        (budget.isNotEmpty().also {
            binding.btnSave.isEnabled = it })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}