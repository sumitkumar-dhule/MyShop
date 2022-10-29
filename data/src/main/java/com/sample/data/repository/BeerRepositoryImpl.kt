package com.sample.data.repository

import com.sample.data.mapper.toBeer
import com.sample.data.mapper.toBeerDetails
import com.sample.data.remote.BeerApi
import com.sample.domain.model.Beer
import com.sample.domain.model.BeerDetails
import com.sample.domain.repository.BeerRepository
import javax.inject.Inject

internal class BeerRepositoryImpl@Inject constructor(private val beerApi: BeerApi):
    BeerRepository {
    override suspend fun getBeers(): List<Beer> {
        return beerApi.getBeers().map { it.toBeer() }
    }

    override suspend fun getBeerById(beerId: String): BeerDetails {
        return beerApi.getBeerById(beerId).map { it.toBeerDetails() }.last()
    }
}