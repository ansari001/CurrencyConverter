package com.test.itfaq.ui

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.test.itfaq.listeners.OnDialogGenericListener
import com.test.itfaq.R
import com.test.itfaq.databinding.ActivityAnagramBinding
import com.test.itfaq.utils.DialogUtil
import java.util.*

class AnagramActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnagramBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnagramBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.anagram)

        binding.btnCheckAnagram.setOnClickListener {
            val str1 = binding.edtAnagramString1.text.toString()
            val str2 = binding.edtAnagramString2.text.toString()
            if (checkValidation(str1, str2)) {
                if (isAnagram(str1, str2)) {
                    showDialog("Both Strings are Anagram.")
                } else {
                    showDialog("Strings are not Anagram!")
                }
            }
        }
    }

    private fun checkValidation(str1: String, str2: String): Boolean {
        if (TextUtils.isEmpty(str1)) {
            showDialog(getString(R.string.msg_first_string))
            return false
        }
        if (TextUtils.isEmpty(str2)) {
            showDialog(getString(R.string.msg_second_string))
            return false
        }
        return true
    }

    private fun showDialog(title: String) {
        DialogUtil.showInfoDialog(
            this@AnagramActivity,
            title,
            object : OnDialogGenericListener {
                override fun onPositiveClick() {

                }
            }
        )
    }

    private fun isAnagram(str1: String, str2: String): Boolean {
        //Both String Length must be Equal
        if (str1.length != str2.length) {
            return false
        }

        //Convert Strings to character Array
        val strArray1 = str1.toCharArray()
        val strArray2 = str2.toCharArray()

        //Sort the Arrays
        Arrays.sort(strArray1)
        Arrays.sort(strArray2)

        //Convert Arrays to String
        val sortedStr1 = String(strArray1)
        val sortedStr2 = String(strArray2)

        //Check Both String Equals or not After Sorting
        //and Return value True or False
        return sortedStr1 == sortedStr2
    }
}