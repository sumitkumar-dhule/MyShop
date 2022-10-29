package com.sample.domain.model

data class BeerDetails(
    val abv: Double,
    val brewers_tips: String,
    val description: String,
    val first_brewed: String,
    val food_pairing: List<String>,
    val id: Int,
    val imageUrl: String,
    val name: String,
    val ph: Double,
    val tagline: String,
)