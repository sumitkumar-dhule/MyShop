package com.sample.myshop.presentation.beer_detail

import com.sample.domain.model.BeerDetails
import com.sample.domain.model.Product

data class ProductState(
    val isLoading: Boolean = false,
    val beer: Product? = null,
    val error: String = ""
)
