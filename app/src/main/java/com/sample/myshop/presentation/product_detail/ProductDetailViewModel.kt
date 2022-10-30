package com.sample.myshop.presentation.product_detail

import android.util.Log
import android.util.StatsLog.logEvent
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.common.Constants
import com.sample.common.Resource
import com.sample.domain.use_case.GetProductDetailsUseCase
import com.sample.myshop.presentation.util.BranchEventLoggerUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import io.branch.referral.util.BRANCH_STANDARD_EVENT
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val branchEventLoggerUtil: BranchEventLoggerUtil
) : ViewModel() {

    private val _state = MutableStateFlow(ProductState())
    val state: StateFlow<ProductState> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_PRODUCT_ID)?.let { productId ->
            getProduct(productId)
        }
    }

    fun getProduct(beerID: String) {
        getProductDetailsUseCase(beerID).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ProductState(beer = result.data)
                }
                is Resource.Error -> {
                    _state.value = ProductState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = ProductState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun logCommerceEventAddToCart() {

        Log.i("Testing", "BRANCH_STANDARD_EVENT.ADD_TO_CART")
        _state.value.beer?.let {
            branchEventLoggerUtil.logEvent(
                it,
                BRANCH_STANDARD_EVENT.ADD_TO_CART
            )
        }
    }

}