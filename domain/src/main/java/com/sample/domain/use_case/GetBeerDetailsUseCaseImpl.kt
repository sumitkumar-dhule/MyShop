package com.sample.domain.use_case

import com.sample.common.Resource
import com.sample.domain.model.BeerDetails
import com.sample.domain.model.Product
import com.sample.domain.repository.BeerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

internal class GetBeerDetailsUseCaseImpl @Inject constructor(private val repository: BeerRepository):GetBeerDetailsUseCase {

    override operator fun invoke(beerId: String): Flow<Resource<Product>> = flow {
        try {
            emit(Resource.Loading())
            val beerDetails = repository.getBeerById(beerId)
            emit(Resource.Success(beerDetails))
        } catch (e: HttpException) {
            emit(Resource.Error(   e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

}