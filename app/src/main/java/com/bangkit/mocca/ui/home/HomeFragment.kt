package com.bangkit.mocca.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.mocca.data.model.UserPreference
import com.bangkit.mocca.databinding.FragmentHomeBinding
import com.bangkit.mocca.utils.ViewModelFactory
import com.bangkit.mocca.utils.dataStore
import com.bangkit.mocca.utils.toMoneyFormat

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        setupViewModel()
        setupAction()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this, ViewModelFactory(UserPreference.getInstance(requireContext().dataStore))
        )[HomeViewModel::class.java]

        viewModel.apply {
            isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }

            getUser().observe(viewLifecycleOwner) { result ->
                val userId = result.idUser
                binding.tvName.text = result.name

                getDashboard(userId)
                dashboard.observe(viewLifecycleOwner) {
                    for (item in it) {
                        binding.tvExpenses.text = item.totalExpense.toString().toMoneyFormat()
                        binding.tvIncome.text = item.totalRevenue.toString().toMoneyFormat()
                        binding.tvBalance.text = item.selisih.toString().toMoneyFormat()
                    }
                }

                getCurrentTransaction(userId)
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

private fun showStatus(isEmpty: Boolean) {
    binding.tvTransactionState.visibility = if (isEmpty) View.VISIBLE else View.GONE
}

private fun showLoading(isLoading: Boolean) {
    binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
}

override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
}
}