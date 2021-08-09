package com.test.itfaq.ui


import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.test.itfaq.listeners.OnDialogGenericListener
import com.test.itfaq.R
import com.test.itfaq.adapters.CurrencyAdapter
import com.test.itfaq.databinding.ActivityCurrencyListBinding
import com.test.itfaq.model.CurrencyModel
import com.test.itfaq.model.Rates
import com.test.itfaq.network.ApiState
import com.test.itfaq.utils.DialogUtil
import com.test.itfaq.viewmodel.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class CurrencyListActivity : AppCompatActivity() {
    private val mainViewModel: CurrencyViewModel by viewModels()
    private lateinit var binding: ActivityCurrencyListBinding
    private lateinit var postAdapter: CurrencyAdapter
    private val currencyList = arrayListOf<CurrencyModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrencyListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerview()
        title = getString(R.string.base_title)
        mainViewModel.getCurrency()

        lifecycleScope.launchWhenStarted {
            mainViewModel._postStateFlow.collect {
                when (it) {
                    is ApiState.Loading -> {
                        binding.recyclerview.isVisible = false
                        binding.progressBar.isVisible = true
                    }
                    is ApiState.Failure -> {
                        binding.recyclerview.isVisible = false
                        binding.progressBar.isVisible = false
                        Log.d("main", "onCreate: ${it.msg}")
                        showInfoDialog()
                    }
                    is ApiState.Success -> {
                        binding.recyclerview.isVisible = true
                        binding.progressBar.isVisible = false
                        parseRates(it.data.rates)
                    }
                    is ApiState.Empty -> {
                        showInfoDialog()
                    }
                }
            }
        }
    }

    private fun showInfoDialog() {
        DialogUtil.showInfoDialog(
            this@CurrencyListActivity,
            getString(R.string.something_went_wrong),
            object : OnDialogGenericListener {
                override fun onPositiveClick() {
                    finish()
                }
            }
        )
    }

    private fun parseRates(rates: Rates) {
        val jsonString = Gson().toJson(rates)
        val jsonObject = JSONObject(jsonString)
        val iter: Iterator<String> = jsonObject.keys()
        while (iter.hasNext()) {
            val key = iter.next()
            try {
                val value: String = jsonObject.getString(key)
                currencyList.add(CurrencyModel(key, String.format("%.2f", value.toDouble())))
            } catch (e: JSONException) {
                // Something went wrong!
            }
        }

        postAdapter.setData(currencyList)
        postAdapter.notifyDataSetChanged()
    }

    private fun initRecyclerview() {
        postAdapter = CurrencyAdapter(this@CurrencyListActivity, ArrayList())
        binding.recyclerview.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@CurrencyListActivity)
            adapter = postAdapter
        }
    }
}