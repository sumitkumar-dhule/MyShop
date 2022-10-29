package com.sample.myshop.presentation.beer_list

import com.sample.domain.model.Beer
import com.sample.domain.model.Product

data class BeerListState(
    val isLoading: Boolean = false,
    val beers: List<Product> = emptyList(),
    val error: String = ""
)
