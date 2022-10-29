package com.sample.myshop

import com.sample.domain.model.Beer
import com.sample.domain.model.BeerDetails

fun getDummyBeer() = Beer(
    id = 1,
    description = "description",
    imageUrl = "imageUrl",
    name = "name",
    tagline = "tagline"

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