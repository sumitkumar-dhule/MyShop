package com.sample.data.repository

import com.sample.data.mapper.toProduct
import com.sample.data.remote.ShopApi
import com.sample.domain.model.Product
import com.sample.domain.repository.ProductRepository
import javax.inject.Inject

internal class ProductRepositoryImpl@Inject constructor(private val shopApi: ShopApi):
    ProductRepository {
    override suspend fun getBeers(): List<Product> {
        return shopApi.getProducts().map { it.toProduct() }
    }

    override suspend fun getBeerById(beerId: String): Product {
        return shopApi.getProductById(beerId).toProduct()
    }
}