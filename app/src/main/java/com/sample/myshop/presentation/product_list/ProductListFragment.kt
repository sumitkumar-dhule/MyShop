package com.sample.myshop.presentation.product_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sample.common.Constants
import com.sample.myshop.R
import com.sample.myshop.databinding.FragmentProductListBinding
import com.sample.myshop.presentation.adapter.ProductAdapter
import com.sample.myshop.presentation.util.BranchHelperUtil
import dagger.hilt.android.AndroidEntryPoint
import io.branch.referral.Branch
import io.branch.referral.util.BRANCH_STANDARD_EVENT
import kotlinx.coroutines.flow.collect
import org.json.JSONObject

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
                value.products.let {
                    productAdapter.differ.submitList(it)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        activity?.let {
            // listener (within Main Activity's onStart)
            Branch.sessionBuilder(it).withCallback { referringParams, error ->
                if (error == null) {

                    // option 1: log data
                    Log.d("BRANCH SDK", referringParams.toString())

                    referringParams?.let {
                        val jsonObject: JSONObject = referringParams
                        val branchLinkClicked = jsonObject.getBoolean("+clicked_branch_link")
                        val productId: String? = jsonObject.getString("PARAM_PRODUCT_ID")

                        productId?.let {
                            if (branchLinkClicked && productId.isNotBlank()) {
                                // option 3: navigate to page
                                val bundle = Bundle().apply {
                                    putString(Constants.PARAM_PRODUCT_ID, productId)
                                }

                                findNavController().navigate(
                                    R.id.action_FirstFragment_to_SecondFragment,
                                    bundle
                                )
                            }
                        }
                    }
                } else {
                    Log.e("BRANCH SDK", error.message)
                }
            }.withData(it.intent.data).init()
        }

    }

}