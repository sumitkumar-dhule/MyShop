package com.sample.data.repository

import com.sample.data.mapper.toBeer
import com.sample.data.mapper.toBeerDetails
import com.sample.data.mapper.toProduct
import com.sample.data.remote.BeerApi
import com.sample.domain.model.Beer
import com.sample.domain.model.BeerDetails
import com.sample.domain.model.Product
import com.sample.domain.repository.BeerRepository
import javax.inject.Inject

internal class BeerRepositoryImpl@Inject constructor(private val beerApi: BeerApi):
    BeerRepository {
    override suspend fun getBeers(): List<Product> {
        return beerApi.getBeers().map { it.toProduct() }
    }

    override suspend fun getBeerById(beerId: String): Product {
        return beerApi.getBeerById(beerId).toProduct()
    }
}