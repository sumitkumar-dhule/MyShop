package com.sample.myshop.presentation.util

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.sample.common.Constants
import com.sample.domain.model.Product
import dagger.hilt.android.qualifiers.ApplicationContext
import io.branch.indexing.BranchUniversalObject
import io.branch.referral.Branch.BranchLinkCreateListener
import io.branch.referral.util.*
import javax.inject.Inject


class BranchHelperUtil @Inject constructor(
    @ApplicationContext val context: Context
) {

    fun logEvent(context: Context, product: Product, event: BRANCH_STANDARD_EVENT) {
        val buo = getBranchUniversalObject(product)
        BranchEvent(event).addContentItems(buo).logEvent(context)
        actionToast(event)
    }

    fun logEvent(product: Product, event: BRANCH_STANDARD_EVENT) {
        val buo = getBranchUniversalObject(product)
        BranchEvent(event).addContentItems(buo).logEvent(context)
        actionToast(event)
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

        actionToast(BRANCH_STANDARD_EVENT.ADD_TO_CART)
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
                ContentMetadata().setRating(
                    product.rating.rate.toDouble(),
                    null,
                    5.0,
                    product.rating.count
                ).setPrice(
                    product.price.toDouble(),
                    CurrencyType.USD
                ).setProductCategory(
                    ProductCategory.APPAREL_AND_ACCESSORIES
                ).setProductCondition(ContentMetadata.CONDITION.EXCELLENT)
            )
    }

    fun shareOwnWay(context: Context, product: Product) {
        val buo = getBranchUniversalObject(product)
        BranchEvent(BRANCH_STANDARD_EVENT.SHARE).addContentItems(buo).logEvent(context)

        val linkProperties = LinkProperties()
            .setChannel("whatsapp")
            .setFeature("sharing")
            .setCampaign("content 123 launch")
            .setStage("new user")
            .addControlParameter("""${'$'}android_deeplink_path""", buo.canonicalUrl)
            .addControlParameter("desktop_url", buo.canonicalUrl)
            .addControlParameter("PARAM_PRODUCT_ID", product.id.toString())

        buo.generateShortUrl(context, linkProperties,
            BranchLinkCreateListener { url, error ->
                if (error == null) {
                    Log.i("MyApp", "got my Branch link to share: $url")

                    Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "Check this out! \nThis deal is awesome: $url")
                        type = "text/plain"
                    }.also {
                        startActivity(context, Intent.createChooser(it, "Share to"), null)
                    }
                }
            })

        actionToast(BRANCH_STANDARD_EVENT.SHARE)
    }


    private fun actionToast(event: BRANCH_STANDARD_EVENT) {
        when (event) {
            BRANCH_STANDARD_EVENT.SHARE -> {
                Toast.makeText(context, "Item Shared", Toast.LENGTH_SHORT).show()
            }
            BRANCH_STANDARD_EVENT.VIEW_ITEM -> {
                Toast.makeText(context, "Item viewed", Toast.LENGTH_SHORT).show()
            }
            BRANCH_STANDARD_EVENT.ADD_TO_CART -> {
                Toast.makeText(context, "Item added to Cart", Toast.LENGTH_SHORT).show()
            }
            BRANCH_STANDARD_EVENT.RATE -> {
                Toast.makeText(context, "Item Rated", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
