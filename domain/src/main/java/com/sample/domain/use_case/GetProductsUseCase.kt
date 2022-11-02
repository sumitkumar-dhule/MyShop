package com.sample.domain.use_case

import com.sample.common.Resource
import com.sample.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface GetProductsUseCase {
    operator fun invoke(): Flow<Resource<List<Product>>>
}