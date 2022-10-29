package com.sample.domain.use_case

import com.sample.domain.getDummyBeerDetails
import com.sample.domain.model.BeerDetails
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
class GetBeerDetailsUseCaseImplTest {

    private lateinit var getBeerDetailsUseCaseImpl: GetBeerDetailsUseCaseImpl
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Mock
    private lateinit var repository: BeerRepository


    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        getBeerDetailsUseCaseImpl = GetBeerDetailsUseCaseImpl(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `Successful Result with List of Beers`() {
        runBlocking {
            whenever(repository.getBeerById("1")).thenReturn(getDummyBeerDetails())
            var beerDetails: BeerDetails? = null
            val output = getBeerDetailsUseCaseImpl.invoke("1")
            output.collect {
                beerDetails = it.data
            }
            assertEquals(getDummyBeerDetails(), beerDetails)
        }
    }

}

