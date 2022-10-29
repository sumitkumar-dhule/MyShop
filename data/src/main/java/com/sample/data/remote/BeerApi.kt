package com.sample.data.remote

import com.sample.data.remote.dto.BeerDetailsDto
import com.sample.data.remote.dto.BeerDto
import com.sample.data.remote.dto.ProductDto
import retrofit2.http.GET
import retrofit2.http.Path

internal interface BeerApi {

    @GET("products")
    suspend fun getBeers(): List<ProductDto>

    @GET("products/{beerId}")
    suspend fun getBeerById(@Path("beerId") beerId: String): ProductDto

}