package com.sample.domain.repository

import com.sample.domain.model.Beer
import com.sample.domain.model.BeerDetails
import com.sample.domain.model.Product

interface ProductRepository {

    suspend fun getBeers(): List<Product>

    suspend fun getBeerById(beerId: String): Product
}