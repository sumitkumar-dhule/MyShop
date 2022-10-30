package com.sample.data.remote

import com.sample.data.remote.dto.BeerDetailsDto
import com.sample.data.remote.dto.BeerDto
import com.sample.data.remote.dto.ProductDto
import retrofit2.http.GET
import retrofit2.http.Path

internal interface ShopApi {

    @GET("products")
    suspend fun getProducts(): List<ProductDto>

    @GET("products/{productId}")
    suspend fun getProductById(@Path("productId") beerId: String): ProductDto

}