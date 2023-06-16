package com.bangkit.mocca.ui.budget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.mocca.data.remote.response.category.ListCategoryItem
import com.bangkit.mocca.databinding.BudgetItemBinding
import com.bangkit.mocca.utils.toMoneyFormat

class BudgetAdapter(private val listCategory: List<ListCategoryItem>): RecyclerView.Adapter<BudgetAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: ListCategoryItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder (var binding: BudgetItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = BudgetItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listCategory.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (category, budget, leftover) = listCategory[position]
        holder.binding.apply {
            tvCategory.text = category
            tvBudget.text = budget.toString().toMoneyFormat()
            val consume = budget-leftover
            tvConsume.text = consume.toString().toMoneyFormat()
            val progress = (consume.toFloat())/(budget.toFloat())*100
            progressBarBudget.progress = progress.toInt()
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listCategory[position])
        }
    }
}
