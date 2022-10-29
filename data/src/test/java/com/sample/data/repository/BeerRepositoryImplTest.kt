package com.sample.data.repository

import com.sample.data.getDummyBeer
import com.sample.data.getDummyBeerDetails
import com.sample.data.getDummyBeerDetailsDto
import com.sample.data.getDummyBeerDto
import com.sample.data.remote.BeerApi
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class BeerRepositoryImplTest {

    private lateinit var beerRepositoryImpl: BeerRepositoryImpl
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Mock
    internal lateinit var beerApi: BeerApi

    @Before
    fun setUp() {
        Dispatchers.
        setMain(mainThreadSurrogate)
        beerRepositoryImpl = BeerRepositoryImpl(beerApi)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `Get list of beers`() = runBlocking {
        whenever(beerApi.getBeers()).thenReturn(listOf(getDummyBeerDto()))
        val beerList = beerRepositoryImpl.getBeers()
        assertEquals(listOf(getDummyBeer()), beerList)
    }

    @Test
    fun `Get list of beer details`() = runBlocking {
        val beerID = "54"
        whenever(beerApi.getBeerById(beerID)).thenReturn(listOf(getDummyBeerDetailsDto()))
        val beerDetails = beerRepositoryImpl.getBeerById(beerID)
        assertEquals(getDummyBeerDetails(), beerDetails)
    }

}