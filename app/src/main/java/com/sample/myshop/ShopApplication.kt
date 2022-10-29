package com.sample.myshop

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.branch.referral.Branch

@HiltAndroidApp
class ShopApplication: Application(){
    override fun onCreate() {
        super.onCreate()

        // Branch logging for debugging
        Branch.enableTestMode()

        // Branch object initialization
        Branch.getAutoInstance(this)
    }
}