package com.bangkit.mocca.ui.transaction.ocr

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.mocca.R
import com.bangkit.mocca.data.remote.response.ocr.OcrResponse
import com.bangkit.mocca.databinding.TransactionItemBinding
import com.bangkit.mocca.databinding.TransactionOcrItemBinding

class ListProductOcrAdapter(val context: Context, private val listProduct: List<OcrResponse>) : RecyclerView.Adapter<ListProductOcrAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = TransactionOcrItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listProduct.size

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val product = listProduct[position]
        holder.tvName.text = product.name_product
        holder.tvPrice.text = product.price.toString()

        holder.itemView.setOnClickListener {
            val view = LayoutInflater.from(context).inflate(R.layout.edit_transaction_ocr_layout, null)
            val productName: EditText = view.findViewById(R.id.edt_product_name)
            val productPrice: EditText = view.findViewById(R.id.edt_product_price)

            productName.setText(product.name_product)
            productPrice.setText(product.price.toString())

            AlertDialog.Builder(context)
                .setView(view)
                .setPositiveButton("Ok") { dialog, _ ->
                    product.name_product = productName.text.toString()
                    val inputPrice = productPrice.text.toString()
                    if (inputPrice.isNotEmpty()) {
                        product.price = Integer.parseInt(inputPrice)
                        notifyDataSetChanged()
                    }
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.cancel()
                }
                .create()
                .show()
        }
    }

    class ListViewHolder(binding: TransactionOcrItemBinding): RecyclerView.ViewHolder(binding.root) {
        val tvName: TextView = binding.tvTransactionName
        val tvPrice: TextView = binding.tvTransactionPrice
    }
}