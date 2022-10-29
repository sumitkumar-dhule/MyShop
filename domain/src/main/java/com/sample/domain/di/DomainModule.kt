package com.sample.domain.di

import com.sample.domain.use_case.GetBeerDetailsUseCase
import com.sample.domain.use_case.GetBeerDetailsUseCaseImpl
import com.sample.domain.use_case.GetBeersUseCase
import com.sample.domain.use_case.GetBeersUseCaseImpl
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
    internal abstract fun bindGetBeerDetailsUseCase(useCaseImpl: GetBeerDetailsUseCaseImpl): GetBeerDetailsUseCase

    @Binds
    @Singleton
    internal abstract fun bindGetBeersUseCase(useCaseImpl: GetBeersUseCaseImpl): GetBeersUseCase
}