package com.sample.domain.use_case

import com.sample.domain.getDummyBeer
import com.sample.domain.model.Beer
import com.sample.domain.repository.BeerRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetBeersUseCaseImplTest {

    private lateinit var getBeersUseCaseImpl: GetBeersUseCaseImpl
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    @Mock
    private lateinit var repository: BeerRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        getBeersUseCaseImpl = GetBeersUseCaseImpl(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `Successful Result with List of Beers`() {
        runBlocking {
            whenever(repository.getBeers()).thenReturn(listOf(getDummyBeer()))
            var beer: Beer? = null
            val output = getBeersUseCaseImpl.invoke()
            output.collect {
               beer = it.data?.first()
            }
            assertEquals(getDummyBeer(), beer)
        }
    }
}

