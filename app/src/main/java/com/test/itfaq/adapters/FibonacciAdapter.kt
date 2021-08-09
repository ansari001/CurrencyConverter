package com.test.itfaq.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.itfaq.databinding.RowFibonacciBinding
import java.math.BigDecimal

class FibonacciAdapter(
    private var fibonacciList: MutableList<BigDecimal>,

    ) :
    RecyclerView.Adapter<FibonacciAdapter.PostViewHolder>() {
    private lateinit var binding: RowFibonacciBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        binding = RowFibonacciBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return PostViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        binding.tvName.text = fibonacciList[position].toString()
    }

    override fun getItemCount(): Int = fibonacciList.size

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun setData(fibList: MutableList<BigDecimal>) {
        this.fibonacciList = fibList
    }
}