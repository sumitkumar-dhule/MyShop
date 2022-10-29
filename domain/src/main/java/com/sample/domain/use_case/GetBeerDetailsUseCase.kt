package com.sample.domain.use_case

import com.sample.common.Resource
import com.sample.domain.model.BeerDetails
import com.sample.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface GetBeerDetailsUseCase  {
    operator fun invoke(beerId: String): Flow<Resource<Product>>
}