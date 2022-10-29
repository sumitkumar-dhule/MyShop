package com.sample.myshop.presentation.beer_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sample.myshop.R
import com.sample.common.Constants
import com.sample.myshop.databinding.FragmentBeerListBinding
import com.sample.myshop.presentation.adapter.BeerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class BeerListFragment : Fragment() {

    private val viewModel by viewModels<BeerListViewModel>()
    private lateinit var beerAdapter: BeerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentBeerListBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        beerAdapter = BeerAdapter()
        binding.rvBeers.adapter = beerAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        beerAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putString(Constants.PARAM_BEER_ID, it.id.toString())
            }
            findNavController().navigate(
                R.id.action_FirstFragment_to_SecondFragment,
                bundle
            )

        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            viewModel.state.collect { value: BeerListState ->

                value.beers.let {
                    beerAdapter.differ.submitList(it)
                }
            }
        }
    }

}