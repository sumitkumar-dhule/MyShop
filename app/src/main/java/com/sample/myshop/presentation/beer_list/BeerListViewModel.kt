package com.sample.myshop.presentation.beer_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.common.Resource
import com.sample.domain.use_case.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BeerListViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(BeerListState())
    val state: StateFlow<BeerListState> = _state

    init {
        getBeers()
    }

    fun getBeers() {
        getProductsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = BeerListState(beers = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = BeerListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = BeerListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}