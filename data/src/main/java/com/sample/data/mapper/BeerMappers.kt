package com.sample.data.mapper

import com.sample.data.remote.dto.BeerDetailsDto
import com.sample.data.remote.dto.BeerDto
import com.sample.domain.model.Beer
import com.sample.domain.model.BeerDetails

internal fun BeerDetailsDto.toBeerDetails(): BeerDetails {
    return BeerDetails(
        id = id,
        description = description,
        imageUrl = imageUrl,
        name = name,
        tagline = tagline,
        food_pairing = food_pairing,
        brewers_tips = brewers_tips,
        abv = abv,
        first_brewed = first_brewed,
        ph = ph,
    )
}

internal fun BeerDto.toBeer(): Beer {
    return Beer(
        id = id,
        description = description,
        imageUrl = imageUrl,
        name = name,
        tagline = tagline
    )
}