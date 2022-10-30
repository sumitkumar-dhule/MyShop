package com.sample.domain.di

import com.sample.domain.use_case.GetProductDetailsUseCase
import com.sample.domain.use_case.GetProductDetailsUseCaseImpl
import com.sample.domain.use_case.GetProductsUseCase
import com.sample.domain.use_case.GetProductsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class DomainModule {
    @Binds
    @Singleton
    internal abstract fun bindGetBeerDetailsUseCase(useCaseImpl: GetProductDetailsUseCaseImpl): GetProductDetailsUseCase

    @Binds
    @Singleton
    internal abstract fun bindGetBeersUseCase(useCaseImpl: GetProductsUseCaseImpl): GetProductsUseCase
}