package com.test.itfaq.network

import com.test.itfaq.model.CurrencyResponse

sealed class ApiState {
    object Loading : ApiState()
    class Failure(val msg: Throwable) : ApiState()
    class Success(val data: CurrencyResponse) : ApiState()
    object Empty : ApiState()
}
