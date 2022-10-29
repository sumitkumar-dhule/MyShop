package com.sample.data.remote

import com.sample.data.remote.dto.BeerDetailsDto
import com.sample.data.remote.dto.BeerDto
import retrofit2.http.GET
import retrofit2.http.Path

internal interface BeerApi {

    @GET("v2/beers")
    suspend fun getBeers(): List<BeerDto>

    @GET("v2/beers/{beerId}")
    suspend fun getBeerById(@Path("beerId") beerId: String): List<BeerDetailsDto>

}