package com.test.itfaq.network

import com.test.itfaq.model.CurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("latest")
    suspend fun getCurrency(
        @Query("access_key") access_key: String
    ): CurrencyResponse
}