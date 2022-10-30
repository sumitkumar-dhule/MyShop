package com.sample.myshop.presentation.product_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sample.myshop.databinding.FragmentProductDetailBinding
import com.sample.myshop.presentation.util.BranchHelperUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private val viewModel by viewModels<ProductDetailViewModel>()
    private lateinit var button: Button
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentProductDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        button = binding.shareButton

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button.setOnClickListener {
            context?.let { context ->
                val branchHelperUtil = BranchHelperUtil(context)

                branchHelperUtil.shareOwnWay(
                    context,
                    viewModel.state.value.beer!!
                )
            }
        }
    }

}