package com.sample.domain.use_case

import com.sample.common.Resource
import com.sample.domain.model.BeerDetails
import kotlinx.coroutines.flow.Flow

interface GetBeerDetailsUseCase  {
    operator fun invoke(beerId: String): Flow<Resource<BeerDetails>>
}