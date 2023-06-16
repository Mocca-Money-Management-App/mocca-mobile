package com.bangkit.mocca.ui.budget

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.mocca.data.model.UserPreference
import com.bangkit.mocca.data.online.response.ListCategoryItem
import com.bangkit.mocca.databinding.FragmentBudgetBinding
import com.bangkit.mocca.utils.ViewModelFactory
import com.bangkit.mocca.utils.dataStore
import com.bangkit.mocca.utils.toCurrencyFormat

class BudgetFragment : Fragment() {
    private var _binding: FragmentBudgetBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BudgetViewModel
    private lateinit var adapter: BudgetAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBudgetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupAction()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this, ViewModelFactory(UserPreference.getInstance(requireContext().dataStore))
        )[BudgetViewModel::class.java]

        viewModel.apply {
            isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }

            getUser().observe(viewLifecycleOwner) {result ->
                val userId = result.idUser

                getCategory(1)
                listCategory.observe(viewLifecycleOwner) {
                    var budget = 0
                    var left = 0
                    for (i in it) {
                        val tmpBudget = i.budget
                        val tmpLeft = i.sisaBudget
                        budget += tmpBudget
                        left += tmpLeft
                    }
                    var consume = budget - left
                    val progress = (consume.toFloat()/budget.toFloat())*100

                    binding.tvLeftover.text = budget.toString().toCurrencyFormat()
                    binding.tvConsume.text = consume.toString().toCurrencyFormat()
                    binding.progressBarBudget.progress = progress.toInt()

                    if (it.isNotEmpty()) {
                        adapter = BudgetAdapter(it)
                        adapter.setOnItemClickCallback(object : BudgetAdapter.OnItemClickCallback {
                            override fun onItemClicked(data: ListCategoryItem) {
                                val intent = Intent(requireContext(), EditBudgetActivity::class.java)
                                intent.putExtra(CATEGORY, data.nameCategory)
                                startActivity(intent)
                            }
                        })
                        binding.rvBudget.adapter = adapter
                    } else {
                        showStatus(true)
                    }
                }
            }
        }
    }

    private fun setupAction() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
        binding.apply {
            rvBudget.layoutManager = gridLayoutManager
            rvBudget.setHasFixedSize(true)
        }

        binding.btnEdit.setOnClickListener {
            val intent = Intent(activity, AddBudgetActivity::class.java)
            activity?.startActivity(intent)
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showStatus(isEmpty: Boolean) {
        binding.tvBudgetStatus.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val CATEGORY = "category"
    }
}