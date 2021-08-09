package com.test.itfaq.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.itfaq.databinding.RowCurrencyBinding
import com.test.itfaq.model.CurrencyModel
import com.test.itfaq.ui.ConverterActivity
import com.test.itfaq.utils.AppUtils
import java.util.*

class CurrencyAdapter(
    private val mContext: Context,
    private var currencyList: ArrayList<CurrencyModel>
) :
    RecyclerView.Adapter<CurrencyAdapter.PostViewHolder>() {
    private lateinit var binding: RowCurrencyBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        binding = RowCurrencyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return PostViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = currencyList[position]
        binding.tvName.text = item.name
        binding.tvRate.text = item.rate
        binding.llParent.setOnClickListener {
            passDataInIntent(item)
        }
        val countryCode = item.name.substring(0, item.name.length - 1)
        val flag = AppUtils.getFlags(countryCode)
        binding.tvFlag.text = flag
    }

    private fun passDataInIntent(item: CurrencyModel) {
        var baseCurrency: CurrencyModel? = null
        currencyList.forEachIndexed { _, model ->
            if (model.name == "USD") {
                baseCurrency = model
            }
        }
        val intent = Intent(mContext, ConverterActivity::class.java)
        intent.putExtra("base_currency", baseCurrency)
        intent.putExtra("selected_currency", item)
        mContext.startActivity(intent)
    }

    override fun getItemCount(): Int = currencyList.size

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    fun setData(list: ArrayList<CurrencyModel>) {
        this.currencyList = list
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}