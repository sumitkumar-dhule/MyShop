package com.sample.domain.repository

import com.sample.domain.model.Beer
import com.sample.domain.model.BeerDetails

interface BeerRepository {

    suspend fun getBeers(): List<Beer>

    suspend fun getBeerById(beerId: String): BeerDetails
}