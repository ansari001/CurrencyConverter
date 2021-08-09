package com.test.itfaq.ui

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.test.itfaq.R
import com.test.itfaq.databinding.ActivityConverterBinding
import com.test.itfaq.model.CurrencyModel

class ConverterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConverterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConverterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.converter)

        val baseCurrency = intent.getSerializableExtra("base_currency") as? CurrencyModel
        val selectedCurrency = intent.getSerializableExtra("selected_currency") as? CurrencyModel

        binding.edtBase.hint = "1"
        binding.edtResult.hint = selectedCurrency?.rate.toString()

        binding.tvBaseCurrency.text = baseCurrency?.name
        binding.tvSelectedCurrency.text = selectedCurrency?.name

        binding.edtBase.doAfterTextChanged {
            if (!TextUtils.isEmpty(it)) {
                val text = it.toString()
                val result = text.toDouble() * selectedCurrency?.rate?.toDouble()!!
                binding.edtResult.setText(String.format("%.2f", result))
            } else {
                binding.edtResult.setText("")
            }
        }
    }
}