package com.sample.domain.use_case

import com.sample.common.Resource
import com.sample.domain.model.Beer
import kotlinx.coroutines.flow.Flow

interface GetBeersUseCase {
    operator fun invoke(): Flow<Resource<List<Beer>>>
}