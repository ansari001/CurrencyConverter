package com.test.itfaq.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.itfaq.R
import com.test.itfaq.adapters.FibonacciAdapter
import com.test.itfaq.databinding.ActivityFibonacciBinding
import kotlinx.coroutines.*
import java.math.BigDecimal

class FibonacciActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    private lateinit var fibonacciList: MutableList<BigDecimal>
    private lateinit var mAdapter: FibonacciAdapter
    private lateinit var binding: ActivityFibonacciBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFibonacciBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val isIterative = intent.getBooleanExtra("isIterative", false)
        if (isIterative) {
            title = getString(R.string.menu_fibonacci_by_iterative)
        } else {
            title = getString(R.string.menu_fibonacci_by_recursive)
        }
        initRecyclerview()

        binding.edtFibonacci.doAfterTextChanged {
            if (TextUtils.isEmpty(binding.edtFibonacci.text.toString().trim())) {
                fibonacciList.clear()
                mAdapter.notifyDataSetChanged()
            }
        }

        binding.btnGenerate.setOnClickListener {
            if (!TextUtils.isEmpty(binding.edtFibonacci.text.toString().trim())) {
                if (this::fibonacciList.isInitialized) {
                    fibonacciList.clear()
                }
                mAdapter.notifyDataSetChanged()
                enableDisableViews(false)
                launch { performSeries(isIterative) }
            } else {
                if (this::fibonacciList.isInitialized) {
                    fibonacciList.clear()
                    mAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun enableDisableViews(isEnable: Boolean) {
        when {
            isEnable -> {
                binding.progressbar.visibility = View.GONE
            }
            else -> {
                binding.progressbar.visibility = View.VISIBLE
            }
        }
        binding.edtFibonacci.isEnabled = isEnable
        binding.btnGenerate.isEnabled = isEnable
        binding.btnGenerate.isClickable = isEnable
    }

    private fun initRecyclerview() {
        mAdapter = FibonacciAdapter(mutableListOf())
        binding.rvFibonacci.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@FibonacciActivity)
            adapter = mAdapter
        }
    }

    private suspend fun performSeries(isIterative: Boolean) {
        val count = binding.edtFibonacci.text.toString()
        /*var fiboList: String*/
        if (!isIterative) {
            withContext(Dispatchers.IO)
            {
                fibonacciList = getRecursiveFibonacci(count.toInt())
            }

            withContext(Dispatchers.Main)
            {
                enableDisableViews(true)
                mAdapter.setData(fibonacciList)
                mAdapter.notifyDataSetChanged()
            }
        } else {
            withContext(Dispatchers.IO)
            {
                fibonacciList = generateFibonacciIterative(count.toInt())
            }
            withContext(Dispatchers.Main)
            {
                enableDisableViews(true)
                mAdapter.setData(fibonacciList)
                mAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun getRecursiveFibonacci(
        count: Int,
        first: BigDecimal = BigDecimal(0),
        second: BigDecimal = BigDecimal(1),
        result: MutableList<BigDecimal> = mutableListOf()
    ): MutableList<BigDecimal> {
        return if (count > 0) {
            result.add(first)
            getRecursiveFibonacci(count - 1, second, first + second, result)
        } else {
            result
        }
    }


    private fun generateFibonacciIterative(n: Int): MutableList<BigDecimal> {
        val result: MutableList<BigDecimal> = mutableListOf()
        for (i in 0 until n) {
            result.add(fibIterative(i))
        }
        return result
    }

    private fun fibIterative(n: Int): BigDecimal {
        if (n < 0) {
            return BigDecimal(-1)
        }
        if (n == 0) {
            return BigDecimal(1)
        }
        var a = BigDecimal(1)
        var b = BigDecimal(1)

        for (i in 2..n) {
            val c = a + b
            a = b
            b = c
        }
        return b
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}