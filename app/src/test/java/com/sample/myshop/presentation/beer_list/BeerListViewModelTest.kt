package com.sample.myshop.presentation.beer_list

import com.sample.common.Resource
import com.sample.domain.model.Beer
import com.sample.domain.use_case.GetProductsUseCase
import com.sample.myshop.BaseViewModelTest
import com.sample.myshop.getDummyBeer
import com.sample.myshop.runBlockingMainTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class BeerListViewModelTest : BaseViewModelTest() {

    private lateinit var beerListViewModel: BeerListViewModel

    @Mock
    lateinit var getProductsUseCase: GetProductsUseCase

    @Before
    fun setUp() {
        beerListViewModel = BeerListViewModel(getProductsUseCase)
    }

    @Test
    fun `Successful Result with List of Beers`() = runBlockingMainTest {
        val inputFlow: Flow<Resource<List<Beer>>> =
            flowOf(Resource.Success(listOf(getDummyBeer())))
        whenever(getProductsUseCase.invoke()).thenReturn(inputFlow)
        beerListViewModel.getBeers()
        assertEquals(getDummyBeer(), beerListViewModel.state.value.beers.first())
    }

}