package com.sample.data

import com.sample.data.remote.dto.BeerDetailsDto
import com.sample.data.remote.dto.BeerDto
import com.sample.data.remote.dto.VolumeDto
import com.sample.domain.model.Beer
import com.sample.domain.model.BeerDetails

fun getDummyBeer() = Beer(
    id = 1,
    description = "description",
    imageUrl = "imageUrl",
    name = "name",
    tagline = "tagline"

)

internal fun getDummyBeerDto() = BeerDto(
    id = 1,
    description = "description",
    imageUrl = "imageUrl",
    name = "name",
    tagline = "tagline",
    food_pairing = listOf("food"),
    first_brewed = "01-01-1800",
    ph = 9.0,
    abv = 5.5,
    volume = VolumeDto("test", 5),
    brewersTips = ""
)

fun getDummyBeerDetails() = BeerDetails(
    abv = 1.2,
    id = 1,
    description = "description",
    imageUrl = "imageUrl",
    name = "name",
    tagline = "tagline",
    ph = 7.0,
    first_brewed = "",
    brewers_tips = "",
    food_pairing = listOf("pairs")
)

internal fun getDummyBeerDetailsDto() = BeerDetailsDto(
    abv = 1.2,
    id = 1,
    description = "description",
    imageUrl = "imageUrl",
    name = "name",
    tagline = "tagline",
    ph = 7.0,
    first_brewed = "",
    brewers_tips = "",
    food_pairing = listOf("pairs"),
    volume = VolumeDto("test", 5)
)