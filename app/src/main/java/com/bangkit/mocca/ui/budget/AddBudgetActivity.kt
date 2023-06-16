package com.bangkit.mocca.ui.budget

import android.content.Intent
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
import com.bangkit.mocca.databinding.ActivityAddBudgetBinding
import com.bangkit.mocca.databinding.ActivityEditBudgetBinding
import com.bangkit.mocca.utils.ViewModelFactory
import com.bangkit.mocca.utils.dataStore

class AddBudgetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBudgetBinding
    private lateinit var viewModel: BudgetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBudgetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupViewModel()
        setupAction()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this, ViewModelFactory(UserPreference.getInstance(this@AddBudgetActivity.dataStore))
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

            var category = 0
            var isChecking = true
            var mCheckedId = 0
            rgCategory1.setOnCheckedChangeListener { _, checkedId ->
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    rgCategory2.clearCheck();
                    rgCategory3.clearCheck()
                    mCheckedId = checkedId;
                }
                isChecking = true;
                when (checkedId) {
                    rbCategory1.id -> {
                        category = 1
                    }
                    rbCategory2.id -> {
                        category = 2
                    }
                    rbCategory3.id -> {
                        category = 3
                    }
                }
            }

            rgCategory2.setOnCheckedChangeListener { _, checkedId ->
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    rgCategory1.clearCheck();
                    rgCategory3.clearCheck()
                    mCheckedId = checkedId;
                }
                isChecking = true;
                when (checkedId) {
                    rbCategory4.id -> {
                        category = 4
                    }
                    rbCategory5.id -> {
                        category = 5
                    }
                    rbCategory6.id -> {
                        category = 6
                    }
                }
            }

            rgCategory3.setOnCheckedChangeListener { _, checkedId ->
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    rgCategory1.clearCheck();
                    rgCategory2.clearCheck()
                    mCheckedId = checkedId;
                }
                isChecking = true;
                when (checkedId) {
                    rbCategory7.id -> {
                        category = 7
                    }
                    rbCategory8.id -> {
                        category = 8
                    }
                    rbCategory9.id -> {
                        category = 9
                    }
                }
            }

            btnSave.setOnClickListener {
                val budget = binding.edBudget.text.toString().toFloat()
                viewModel.apply {
                    isLoading.observe(this@AddBudgetActivity) {
                        showLoading(it)
                    }

                    getUser().observe(this@AddBudgetActivity) { result ->
                        addCategory(1, category, budget)
                        success.observe(this@AddBudgetActivity) {
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
                val intent = Intent(this@AddBudgetActivity, MainActivity::class.java)
                startActivity(intent,
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this@AddBudgetActivity).toBundle())
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