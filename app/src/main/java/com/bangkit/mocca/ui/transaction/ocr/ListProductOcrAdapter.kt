package com.bangkit.mocca.ui.transaction.ocr

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.mocca.data.remote.response.OcrResponse
import com.bangkit.mocca.databinding.TransactionItemBinding

class ListProductOcrAdapter(private val listProduct: List<OcrResponse>) : RecyclerView.Adapter<ListProductOcrAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = TransactionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listProduct.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val product = listProduct[position]
        holder.tvName.text = product.name_product
        holder.tvCategory.text = product.category
        holder.tvPrice.text = product.price.toString()
    }

    class ListViewHolder(binding: TransactionItemBinding): RecyclerView.ViewHolder(binding.root) {
        val tvName: TextView = binding.tvTransactionName
        val tvCategory: TextView = binding.tvCategory
        val tvPrice: TextView = binding.tvTransactionPrice
    }
}