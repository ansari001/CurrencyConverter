package com.test.itfaq.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.itfaq.network.ApiState

import com.test.itfaq.repo.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel
@Inject
constructor(private val mainRepository: CurrencyRepository) : ViewModel() {

    private val postStateFlow:MutableStateFlow<ApiState>
    = MutableStateFlow(ApiState.Empty)

    val _postStateFlow:StateFlow<ApiState> = postStateFlow

    fun getCurrency() = viewModelScope.launch {
        postStateFlow.value = ApiState.Loading
        mainRepository.getCurrency()
            .catch { e->
               postStateFlow.value=ApiState.Failure(e)
            }.collect { data->
                postStateFlow.value=ApiState.Success(data)
            }
    }
}