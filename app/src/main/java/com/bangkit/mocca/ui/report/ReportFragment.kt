package com.bangkit.mocca.ui.report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.mocca.R
import com.bangkit.mocca.data.model.UserPreference
import com.bangkit.mocca.databinding.FragmentHomeBinding
import com.bangkit.mocca.databinding.FragmentReportBinding
import com.bangkit.mocca.ui.home.HomeAdapter
import com.bangkit.mocca.ui.home.HomeViewModel
import com.bangkit.mocca.utils.ViewModelFactory
import com.bangkit.mocca.utils.dataStore
import com.bangkit.mocca.utils.toCurrencyFormat

class ReportFragment : Fragment() {
    private var _binding: FragmentReportBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ReportViewModel
    private lateinit var adapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentReportBinding.bind(view)

        setupViewModel()
        setupAction()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this, ViewModelFactory(UserPreference.getInstance(requireContext().dataStore))
        )[ReportViewModel::class.java]

        viewModel.apply {
            isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }

            getUser().observe(viewLifecycleOwner) {result ->
                val userId = result.idUser

                getDashboard(userId)
                dashboard.observe(viewLifecycleOwner) {
                    for (item in it) {
                        binding.tvTotalExpenses.text = item.totalExpense.toString().toCurrencyFormat()
                        binding.tvTotalIncome.text = item.totalRevenue.toString().toCurrencyFormat()
                        binding.tvDifference.text = item.selisih.toString().toCurrencyFormat()
                    }
                }

                getTransaction(userId)
                listTransaction.observe(viewLifecycleOwner) {
                    if (it.isNotEmpty()) {
                        adapter = HomeAdapter(it)
                        binding.rvTransactions.adapter = adapter
                    } else {
                        showStatus(true)
                    }
                }
            }
        }
    }

    private fun setupAction() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.apply {
            rvTransactions.layoutManager = layoutManager
            rvTransactions.setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showStatus(isEmpty: Boolean) {
        binding.tvBudgetStatus.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}