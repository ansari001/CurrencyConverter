package com.test.itfaq.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.itfaq.databinding.RowMenuBinding

class MenuAdapter(
    private var menuItems: ArrayList<String>,
    private val mListener: OnMainMenuListener
) :
    RecyclerView.Adapter<MenuAdapter.PostViewHolder>() {
    private lateinit var binding: RowMenuBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        binding = RowMenuBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return PostViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        binding.tvName.text = menuItems[position]
        binding.llParent.setOnClickListener {
            mListener.onMenuClicked(position)
        }
    }

    override fun getItemCount(): Int = menuItems.size

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    interface OnMainMenuListener {
        fun onMenuClicked(position: Int)
    }
}