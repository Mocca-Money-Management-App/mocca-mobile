package com.bangkit.mocca.ui.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.mocca.data.remote.response.transaction.ListTransactionItem
import com.bangkit.mocca.databinding.TransactionItemBinding
import com.bangkit.mocca.utils.toCurrencyFormat

class HomeAdapter(private val listTransaction: List<ListTransactionItem>): RecyclerView.Adapter<HomeAdapter.ListViewHolder>() {
    class ListViewHolder (var binding: TransactionItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = TransactionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listTransaction.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (userId, category, product, price, createdAt) = listTransaction[position]
        holder.binding.apply {
            tvTransactionName.text = product
            tvCategory.text = category
            if (category == "Pemasukan") {
                tvCategory.setTextColor(Color.parseColor("#70D4B3"))
                tvTransactionPrice.setTextColor(Color.parseColor("#70D4B3"))
            } else {
                tvCategory.setTextColor(Color.parseColor("#FF605F"))
                tvTransactionPrice.setTextColor(Color.parseColor("#FF605F"))
            }
            val nominal = price.toString()
            tvTransactionPrice.text = nominal.toCurrencyFormat()
            val date = settingDate(createdAt)
            tvTransactionDate.text = date
        }
    }

    private fun settingDate(timestamp: String): String {
        val year1 = timestamp[0]
        val year2 = timestamp[1]
        val year3 = timestamp[2]
        val year4 = timestamp[3]
        val month1 = timestamp[5]
        val month2 = timestamp[6]
        val date1 = timestamp[8]
        val date2 = timestamp[9]

        var month = "$month1$month2"
        when (month) {
            "01" -> { month = "January" }
            "02" -> { month = "February" }
            "03" -> { month = "March" }
            "04" -> { month = "April" }
            "05" -> { month = "May" }
            "06" -> { month = "June" }
            "07" -> { month = "July" }
            "08" -> { month = "August" }
            "09" -> { month = "September" }
            "10" -> { month = "October" }
            "11" -> { month = "November" }
            "12" -> { month = "December" }
        }
        return "$date1$date2 $month $year1$year2$year3$year4"
    }
}