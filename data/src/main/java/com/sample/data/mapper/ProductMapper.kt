package com.sample.data.mapper

import com.sample.data.remote.dto.ProductDto
import com.sample.data.remote.dto.RatingDto
import com.sample.domain.model.Product
import com.sample.domain.model.Rating

internal fun ProductDto.toProduct(): Product {
    return Product(
        id = id,
        description = description,
        image = image,
        title = title,
        price = price.toString(),
        category = category,
        rating = rating.toRating()
    )
}

internal fun RatingDto.toRating(): Rating {
    return Rating(
        count = count,
        rate = rate.toFloat()
    )
}