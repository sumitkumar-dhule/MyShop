package com.sample.myshop.presentation.product_list

import com.sample.domain.model.Product

data class ProductListState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val error: String = ""
)
