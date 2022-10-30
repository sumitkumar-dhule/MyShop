package com.sample.myshop.presentation.util

import android.content.Context
import com.sample.common.Constants
import com.sample.domain.model.Product
import dagger.hilt.android.qualifiers.ApplicationContext
import io.branch.indexing.BranchUniversalObject
import io.branch.referral.util.BRANCH_STANDARD_EVENT
import io.branch.referral.util.BranchEvent
import io.branch.referral.util.ContentMetadata
import io.branch.referral.util.CurrencyType
import javax.inject.Inject

class BranchEventLoggerUtil @Inject constructor(
    @ApplicationContext val context: Context
) {

    fun logEvent(context: Context, product: Product, event: BRANCH_STANDARD_EVENT) {
        val buo = getBranchUniversalObject(product)
        BranchEvent(event).addContentItems(buo).logEvent(context)
    }

    fun logEvent(product: Product, event: BRANCH_STANDARD_EVENT) {
        val buo = getBranchUniversalObject(product)
        BranchEvent(event).addContentItems(buo).logEvent(context)
    }

    fun getBranchUniversalObject(product: Product): BranchUniversalObject {
        return BranchUniversalObject()
            .setCanonicalIdentifier("${product.id}")
            .setCanonicalUrl("${Constants.BASE_URL}product/${product.id}")
            .setTitle(product.title)
            .setContentDescription(product.category)
            .setContentImageUrl(product.image)
            .setContentIndexingMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
            .setLocalIndexMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
            .setContentMetadata(ContentMetadata().addCustomMetadata("product", product.title))
            .setContentMetadata(
                ContentMetadata().setPrice(
                    product.price.toDouble(),
                    CurrencyType.USD
                )
            )
            .setContentMetadata(
                ContentMetadata().setRating(
                    product.rating.count.toDouble(),
                    null,
                    5.0,
                    product.rating.count
                )
            )
    }


}
