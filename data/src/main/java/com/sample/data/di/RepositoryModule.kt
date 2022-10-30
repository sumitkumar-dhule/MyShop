package com.sample.data.di

import com.sample.data.repository.ProductRepositoryImpl
import com.sample.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    internal abstract fun bindBeerRepository(beerRepository: ProductRepositoryImpl): ProductRepository
}