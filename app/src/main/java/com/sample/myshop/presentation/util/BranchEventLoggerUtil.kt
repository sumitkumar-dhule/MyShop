package com.sample.myshop.presentation.util

import android.content.Context
import com.sample.common.Constants
import com.sample.domain.model.Product
import dagger.hilt.android.qualifiers.ApplicationContext
import io.branch.indexing.BranchUniversalObject
import io.branch.referral.util.*
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

    fun logCommerceEventAddToCart(product: Product) {
        val buo = BranchUniversalObject()
            .setCanonicalIdentifier("${product.id}")
            .setCanonicalUrl("${Constants.BASE_URL}products/${product.id}")
            .setTitle(product.title)
            .setContentDescription(product.category)
            .setContentImageUrl(product.image)
            .setContentIndexingMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
            .setLocalIndexMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
            .setContentMetadata(ContentMetadata().addCustomMetadata("product", product.title))

            .setContentMetadata(
                ContentMetadata()
                    .setAddress(
                        "Street_Name",
                        "test city",
                        "test_state",
                        "test_country",
                        "test_postal_code"
                    )
                    .setLocation(-151.67, -124.0)
                    .setProductBrand("test_prod_brand")
                    .setProductCategory(ProductCategory.APPAREL_AND_ACCESSORIES)
                    .setProductName(product.title)
                    .setProductCondition(ContentMetadata.CONDITION.EXCELLENT)
                    .setProductVariant("test_prod_variant")
                    .setQuantity(1.0)
                    .setSku("test_sku")
                    .setContentSchema(BranchContentSchema.COMMERCE_PRODUCT)
                    .setRating(
                        product.rating.count.toDouble(),
                        product.rating.count.toDouble(),
                        5.0,
                        product.rating.count
                    )
            )

        val tax = product.price.toDouble() * 0.18
        BranchEvent(BRANCH_STANDARD_EVENT.ADD_TO_CART)
            .setAffiliation("test_affiliation")
            .setCustomerEventAlias("my_custom_alias")
            .setCoupon("Coupon Code")
            .setCurrency(CurrencyType.USD)
            .setDescription("Customer added item to cart")
            .setShipping(0.0)
            .setTax(tax)
            .setRevenue(product.price.toDouble() + tax)
            .setSearchQuery("Test Search query")
            .addContentItems(buo)
            .logEvent(context)
    }

    fun getBranchUniversalObject(product: Product): BranchUniversalObject {
        return BranchUniversalObject()
            .setCanonicalIdentifier("${product.id}")
            .setCanonicalUrl("${Constants.BASE_URL}products/${product.id}")
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
