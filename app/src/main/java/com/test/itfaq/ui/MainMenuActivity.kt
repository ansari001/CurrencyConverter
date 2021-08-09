package com.test.itfaq.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.itfaq.R
import com.test.itfaq.adapters.MenuAdapter
import com.test.itfaq.databinding.ActivityMainMenuBinding

class MainMenuActivity : AppCompatActivity(), MenuAdapter.OnMainMenuListener {
    private lateinit var binding: ActivityMainMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.menu)
        bindMenuList(getMenuData())
    }

    private fun bindMenuList(menuData: ArrayList<String>) {
        val postAdapter = MenuAdapter(menuData, this@MainMenuActivity)
        binding.rvMenuList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainMenuActivity)
            adapter = postAdapter
        }
    }

    private fun getMenuData(): ArrayList<String> {
        val list = arrayListOf<String>()
        list.add(getString(R.string.menu_currency_converter))
        list.add(getString(R.string.menu_anagram))
        list.add(getString(R.string.menu_fibonacci_by_recursive))
        list.add(getString(R.string.menu_fibonacci_by_iterative))
        return list
    }

    override fun onMenuClicked(position: Int) {
        if (position == 0) {
            startActivity(Intent(this@MainMenuActivity, CurrencyListActivity::class.java))
        } else if (position == 1) {
            startActivity(Intent(this@MainMenuActivity, AnagramActivity::class.java))
        } else if (position == 2) {
            val intent = Intent(this@MainMenuActivity, FibonacciActivity::class.java)
            intent.putExtra("isIterative", false)
            startActivity(intent)
        } else {
            val intent = Intent(this@MainMenuActivity, FibonacciActivity::class.java)
            intent.putExtra("isIterative", true)
            startActivity(intent)
        }
    }
}