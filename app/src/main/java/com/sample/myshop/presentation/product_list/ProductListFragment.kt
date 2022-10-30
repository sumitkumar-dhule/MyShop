package com.sample.myshop.presentation.product_list

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
import com.sample.myshop.databinding.FragmentProductListBinding
import com.sample.myshop.presentation.adapter.ProductAdapter
import com.sample.myshop.presentation.util.BranchHelperUtil
import dagger.hilt.android.AndroidEntryPoint
import io.branch.referral.util.BRANCH_STANDARD_EVENT
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

        val binding = FragmentProductListBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        productAdapter = ProductAdapter()
        binding.rvBeers.adapter = productAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productAdapter.setOnItemClickListener { product ->

            context?.let { context ->
                val branchHelperUtil = BranchHelperUtil(context)

                branchHelperUtil.logEvent(
                    context,
                    product,
                    BRANCH_STANDARD_EVENT.VIEW_ITEM
                )
            }

            val bundle = Bundle().apply {
                putString(Constants.PARAM_PRODUCT_ID, product.id.toString())
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