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
import com.sample.myshop.presentation.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.branch.indexing.BranchUniversalObject
import io.branch.referral.util.BRANCH_STANDARD_EVENT
import io.branch.referral.util.BranchEvent
import io.branch.referral.util.ContentMetadata
import io.branch.referral.util.CurrencyType
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private val viewModel by viewModels<ProductListViewModel>()
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentBeerListBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        productAdapter = ProductAdapter()
        binding.rvBeers.adapter = productAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productAdapter.setOnItemClickListener {

            val buo = BranchUniversalObject()
                .setCanonicalIdentifier("${it.id}")
                .setCanonicalUrl("${Constants.BASE_URL}product/${it.id}")
                .setTitle(it.title)
                .setContentDescription(it.category)
                .setContentImageUrl(it.image)
                .setContentIndexingMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
                .setLocalIndexMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
                .setContentMetadata(ContentMetadata().addCustomMetadata("product", it.title))
                .setContentMetadata(
                    ContentMetadata().setPrice(
                        it.price.toDouble(),
                        CurrencyType.USD
                    )
                )
                .setContentMetadata(
                    ContentMetadata().setRating(
                        it.rating.count.toDouble(),
                        null,
                        5.0,
                        it.rating.count
                    )
                )

            BranchEvent(BRANCH_STANDARD_EVENT.VIEW_ITEM).addContentItems(buo).logEvent(context)

            val bundle = Bundle().apply {
                putString(Constants.PARAM_PRODUCT_ID, it.id.toString())
            }
            findNavController().navigate(
                R.id.action_FirstFragment_to_SecondFragment,
                bundle
            )

        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            viewModel.state.collect { value: ProductListState ->

                value.beers.let {
                    productAdapter.differ.submitList(it)
                }
            }
        }
    }

}