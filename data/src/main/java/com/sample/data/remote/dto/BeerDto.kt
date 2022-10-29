package com.sample.data.remote.dto

import com.google.gson.annotations.SerializedName

internal data class BeerDto(
    val abv: Double,
    @SerializedName("brewers_tips")
    val brewersTips: String,
    val description: String,
    val first_brewed: String,
    val food_pairing: List<String>,
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    val name: String,
    val ph: Double,
    val tagline: String,
    val volume: VolumeDto
)