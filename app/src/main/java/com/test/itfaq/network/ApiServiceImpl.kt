package com.test.itfaq.network


import com.test.itfaq.model.CurrencyResponse
import com.test.itfaq.utils.AppConstants
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val apiService: ApiService) {

    suspend fun getCurrency():CurrencyResponse = apiService.getCurrency(AppConstants.ACCESS_KEY)
}