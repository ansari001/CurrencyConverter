package com.test.itfaq.repo


import com.test.itfaq.model.CurrencyResponse
import com.test.itfaq.network.ApiServiceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CurrencyRepository
@Inject
constructor(private val apiServiceImpl: ApiServiceImpl) {

    fun getCurrency(): Flow<CurrencyResponse> = flow {
        emit(apiServiceImpl.getCurrency())
    }.flowOn(Dispatchers.IO)
}