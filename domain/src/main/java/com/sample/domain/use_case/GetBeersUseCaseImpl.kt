package com.sample.domain.use_case

import com.sample.common.Resource
import com.sample.domain.model.Beer
import com.sample.domain.repository.BeerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

internal class GetBeersUseCaseImpl @Inject constructor(private val repository: BeerRepository) :
    GetBeersUseCase {

    override operator fun invoke(): Flow<Resource<List<Beer>>> = flow {
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