package com.sample.myshop.presentation.beer_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.common.Constants
import com.sample.common.Resource
import com.sample.domain.use_case.GetBeerDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BeerDetailViewModel @Inject constructor(
    private val getBeerDetailsUseCase: GetBeerDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(BeerDetailState())
    val state: StateFlow<BeerDetailState> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_BEER_ID)?.let { coinId ->
            getBeer(coinId)
        }
    }

    fun getBeer(beerID: String) {
        getBeerDetailsUseCase(beerID).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = BeerDetailState(beer = result.data)
                }
                is Resource.Error -> {
                    _state.value = BeerDetailState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = BeerDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}