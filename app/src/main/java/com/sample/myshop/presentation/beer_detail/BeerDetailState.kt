package com.sample.myshop.presentation.beer_detail

import com.sample.domain.model.BeerDetails

data class BeerDetailState(
    val isLoading: Boolean = false,
    val beer: BeerDetails? = null,
    val error: String = ""
)
