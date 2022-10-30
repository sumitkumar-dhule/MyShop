package com.sample.domain.use_case

import com.sample.common.Resource
import com.sample.domain.model.Product
import com.sample.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

internal class GetProductsUseCaseImpl @Inject constructor(private val repository: ProductRepository) :
    GetProductsUseCase {

    override operator fun invoke(): Flow<Resource<List<Product>>> = flow {
        try {
            emit(Resource.Loading())
            val beers = repository.getBeers()
            emit(Resource.Success(beers))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}