package com.sample.myshop.presentation.product_detail

import com.sample.domain.model.Product

data class ProductState(
    val isLoading: Boolean = false,
    val product: Product? = null,
    val error: String = ""
)
